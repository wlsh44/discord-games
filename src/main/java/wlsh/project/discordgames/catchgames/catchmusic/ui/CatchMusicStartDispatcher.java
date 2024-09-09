package wlsh.project.discordgames.catchgames.catchmusic.ui;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.discord.ChannelRepository;
import wlsh.project.discordgames.discord.command.ICommand;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static wlsh.project.discordgames.catchgames.catchmusic.ui.ChannelValidator.checkInAudioChannel;

@Component
@RequiredArgsConstructor
public class CatchMusicStartDispatcher implements ICommand {
    private final ChannelRepository channelRepository;

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Start Game";
    }

    @Override
    public List<OptionData> getOptions() {
        return Collections.emptyList();
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Member member = requireNonNull(event.getMember());
        GuildVoiceState memberVoiceState = requireNonNull(member.getVoiceState());
        checkInAudioChannel(memberVoiceState);

        Member bot = requireNonNull(event.getGuild()).getSelfMember();
        GuildVoiceState botVoiceState = requireNonNull(bot.getVoiceState());

        channelRepository.save(event.getChannelId(), event.getChannel());

        openBotIfNotInAudioChannel(event, botVoiceState, memberVoiceState);
    }

    private void openBotIfNotInAudioChannel(SlashCommandInteractionEvent event, GuildVoiceState botVoiceState, GuildVoiceState memberVoiceState) {
        if (!botVoiceState.inAudioChannel()) {
            event.getGuild().getAudioManager().openAudioConnection(memberVoiceState.getChannel());
            event.reply("Start!").queue();
        }
    }
}
