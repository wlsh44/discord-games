package wlsh.project.discordgames.catchmusic;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchmusic.application.TitleHintResult;
import wlsh.project.discordgames.catchmusic.application.hint.AlbumHintService;
import wlsh.project.discordgames.catchmusic.application.hint.ArtistHintService;
import wlsh.project.discordgames.catchmusic.application.hint.MusicNameHintService;
import wlsh.project.discordgames.catchmusic.application.hint.dto.AlbumHintResult;
import wlsh.project.discordgames.catchmusic.application.hint.dto.ArtistHintResult;
import wlsh.project.discordgames.discord.command.ICommand;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static wlsh.project.discordgames.catchmusic.ChannelValidator.checkValidChannelState;

@Component
@RequiredArgsConstructor
public class HintDispatcher implements ICommand {

    private final MusicNameHintService musicNameHintService;
    private final AlbumHintService albumHintService;
    private final ArtistHintService artistHintService;

    @Override
    public String getName() {
        return "hint";
    }

    @Override
    public String getDescription() {
        return "Hint about music";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.STRING, "hint", "힌트", true)
                        .addChoice("앨범 표지", "album")
                        .addChoice("가수", "artist")
                        .addChoice("제목", "title")
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        checkValidChannelState(event.getMember(), event.getGuild(), event.getChannel());

        String hint = requireNonNull(event.getOption("hint")).getAsString();

        String id = event.getGuild().getId();
        switch (hint) {
            case "album" -> {
                AlbumHintResult albumHint = albumHintService.getAlbumHint(id);
                MessageEmbed embed = new EmbedBuilder()
                        .setTitle("앨범 힌트")
                        .setImage(albumHint.albumUrl())
                        .setDescription("**이름** : `%s\n`".formatted(albumHint.albumName()))
                        .appendDescription("**발매일** : `%s`".formatted(albumHint.releaseDate()))
                        .build();
                event.replyEmbeds(embed).queue();
            }
            case "artist" -> {
                ArtistHintResult artistHint = artistHintService.getArtistHint(id);
                MessageEmbed embed = new EmbedBuilder()
                        .setTitle("가수 힌트")
                        .setDescription("**이름** : `%s`".formatted(artistHint.artist()))
                        .setImage(artistHint.artistUrl())
                        .build();
                event.replyEmbeds(embed).queue();
            }
            case "title" -> {
                TitleHintResult musicNameHint = musicNameHintService.getMusicNameHint(id);
                String totalHint = musicNameHint.title() + "(총 글자 수: %d 한글: %s 영어: %s 특수 문자: %s)".formatted(
                        musicNameHint.length(),
                        musicNameHint.hangul() ? "✅" : "❌",
                        musicNameHint.english() ? "✅" : "❌",
                        musicNameHint.specialCharacter() ? "✅" : "❌"
                );
                MessageEmbed embed = new EmbedBuilder()
                        .setTitle("제목 힌트")
                        .setDescription("`%s`".formatted(totalHint))
                        .build();
                event.replyEmbeds(embed).queue();
            }
            default -> {
                throw new RuntimeException("힌트 없음");
            }
        }
    }
}
