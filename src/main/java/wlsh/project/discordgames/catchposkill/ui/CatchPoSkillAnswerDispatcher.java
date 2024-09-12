package wlsh.project.discordgames.catchposkill.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchposkill.application.CatchPoSkillAnswerUseCase;
import wlsh.project.discordgames.catchposkill.application.dto.CatchPoSkillAnswerResult;
import wlsh.project.discordgames.catchposkill.domain.PoSkill;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.common.catchgames.domain.Player;
import wlsh.project.discordgames.common.ui.StatusHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class CatchPoSkillAnswerDispatcher extends ListenerAdapter {

    private final CatchPoSkillAnswerUseCase catchPoSkillAnswerUseCase;
    private final StatusHandler statusHandler;
    private final CatchPoSkillCurrentRoundHandler catchPokemonCurrentRoundHandler;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        Message message = event.getMessage();

        if (author.isBot()) {
            return;
        }
        try {
            if (!event.getChannel().getName().equals("캐치포스킬")) {
                return;
            }

            CatchGameId catchGameId = new CatchGameId(event.getGuild().getId(), event.getChannel().getId());
            CatchPoSkillAnswerResult answer = catchPoSkillAnswerUseCase.answer(
                    catchGameId,
                    new Player(author.getId(), author.getName(), message.getContentDisplay())
            );
            switch (answer.status()) {
                case CORRECT -> {
                    var content = (CatchPoSkillAnswerResult.CorrectContent) answer.content();
                    sendAnswerInfo(event, author.getName(), content.poSkill());
                    statusHandler.sendStatus(event, content.status());
                    catchPokemonCurrentRoundHandler.show(catchGameId, event.getChannel());
                }
                case FINISH -> {
                    var content = (CatchPoSkillAnswerResult.FinishContent) answer.content();
                    statusHandler.sendStatus(event, content.status());
                    event.getChannel().sendMessage("끝").queue();
                }
            }
        } catch (Exception e) {
            log.error("", e);
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }

    private void sendAnswerInfo(MessageReceivedEvent event, String name, PoSkill poSkill) {
        MessageEmbed embed = new EmbedBuilder()
                .setDescription("**정답** : `%s`\n".formatted(name))
                .appendDescription("`%s(%s)`\n".formatted(poSkill.koName(), poSkill.enName()))
                .appendDescription("`%s 세대`\n".formatted(poSkill.generation()))
                .build();
        event.getChannel().sendMessageEmbeds(embed).queue();
    }
}
