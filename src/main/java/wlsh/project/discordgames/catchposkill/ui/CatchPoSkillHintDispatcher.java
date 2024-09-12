package wlsh.project.discordgames.catchposkill.ui;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchmusic.application.hint.dto.TitleHintResult;
import wlsh.project.discordgames.catchposkill.application.CatchPoSkillNameHintUseCase;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.discord.command.ICommand;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CatchPoSkillHintDispatcher implements ICommand {

    private final CatchPoSkillNameHintUseCase catchPoSkillNameHintUseCase;

    @Override
    public String getName() {
        return "poshint";
    }

    @Override
    public String getDescription() {
        return "Hint about poskill";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
//                new OptionData(OptionType.STRING, "hint", "힌트", true)
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (!event.getChannel().getName().equals("캐치포스킬")) {
            return;
        }
        CatchGameId catchGameId = new CatchGameId(event.getGuild().getId(), event.getChannelId());
        TitleHintResult musicNameHint = catchPoSkillNameHintUseCase.getAnswerHint(catchGameId);
        String totalHint = musicNameHint.title() + "(총 글자 수: %d)".formatted(
                musicNameHint.length()
        );
        MessageEmbed embed = new EmbedBuilder()
                .setTitle("제목 힌트")
                .setDescription("`%s`".formatted(totalHint))
                .build();
        event.replyEmbeds(embed).queue();
    }
}