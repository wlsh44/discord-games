package wlsh.project.discordgames.pokemon.ui;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchmusic.application.hint.dto.TitleHintResult;
import wlsh.project.discordgames.discord.command.ICommand;
import wlsh.project.discordgames.pokemon.application.CatchPokemonNameHintService;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Component
@RequiredArgsConstructor
public class CatchPokemonHintDispatcher implements ICommand {

    private final CatchPokemonNameHintService catchPokemonNameHintService;

    @Override
    public String getName() {
        return "pohint";
    }

    @Override
    public String getDescription() {
        return "Hint about music";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
//                new OptionData(OptionType.STRING, "hint", "힌트", true)
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
//        checkValidChannelState(event.getMember(), event.getGuild(), event.getChannel());
        if (!event.getChannel().getName().equals("캐치포켓몬")) {
            return;
        }
//        String hint = requireNonNull(event.getOption("pohint")).getAsString();
        TitleHintResult musicNameHint = catchPokemonNameHintService.getNameHint(event.getGuild().getId());
        String totalHint = musicNameHint.title() + "(총 글자 수: %d)".formatted(
                musicNameHint.length()
//                musicNameHint.hangul() ? "✅" : "❌",
//                musicNameHint.english() ? "✅" : "❌",
//                musicNameHint.specialCharacter() ? "✅" : "❌"
        );
        MessageEmbed embed = new EmbedBuilder()
                .setTitle("제목 힌트")
                .setDescription("`%s`".formatted(totalHint))
                .build();
        event.replyEmbeds(embed).queue();
    }
}