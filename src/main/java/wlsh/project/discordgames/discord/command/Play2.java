package wlsh.project.discordgames.discord.command;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.discord.AudioPlayerService;
import wlsh.project.discordgames.discord.GuildMusicManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Play2 implements ICommand {

    private final AudioPlayerService audioPlayerService;

    @Override
    public String getName() {
        return "play2";
    }

    @Override
    public String getDescription() {
        return "Play a song";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.STRING, "username", "Name of the song to play.", true));
        options.add(new OptionData(OptionType.STRING, "artist", "Artist of the song.", false));
        return options;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        assert member != null;
        GuildVoiceState memberVoiceState = member.getVoiceState();

        assert memberVoiceState != null;
        if (!memberVoiceState.inAudioChannel()) {
            throw new RuntimeException("You need to be in a voice channel.");
        }

        Member self = Objects.requireNonNull(event.getGuild()).getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();

        assert selfVoiceState != null;
        if (!selfVoiceState.inAudioChannel()) {
            event.getGuild().getAudioManager().openAudioConnection(memberVoiceState.getChannel());
        } else {
            if (selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
                throw new RuntimeException("You need to be in the same channel as me.");
            }
        }

        String name = Objects.requireNonNull(event.getOption("username")).getAsString();
        String artist = Optional.ofNullable(event.getOption("artist"))
                .map(OptionMapping::getAsString)
                .orElse("");

        audioPlayerService.play(event.getGuild().getId(), name, artist);
//        event.getChannel().sendMessage("음악을 찾는 중...").queue();

        GuildMusicManager guildMusicManager = audioPlayerService.getGuildMusicManager(event.getGuild().getId());

        event.deferReply(true).queue();
        checkIsPlaying(guildMusicManager);

        AudioTrackInfo info = guildMusicManager.getPlayingTrackInfo();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Playing:");
        embedBuilder.setDescription("**Name:** `" + info.title + "`");
        embedBuilder.appendDescription("\n**Author:** `" + info.author + "`");
        embedBuilder.appendDescription("\n**URL:** `" + info.uri + "`");
        event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
    }

    private void checkIsPlaying(GuildMusicManager guildMusicManager) {
        int retryCount = 0;
        while (!guildMusicManager.isPlaying() && retryCount < 5) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retryCount++;
        }
        if (!guildMusicManager.isPlaying()) {
            throw new RuntimeException("Failed to display song details.");
        }
    }
}