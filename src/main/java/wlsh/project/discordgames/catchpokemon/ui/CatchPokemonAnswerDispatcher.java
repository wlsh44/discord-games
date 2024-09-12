package wlsh.project.discordgames.catchpokemon.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.common.catchgames.domain.Player;
import wlsh.project.discordgames.common.catchgames.ui.StatusHandler;
import wlsh.project.discordgames.catchpokemon.application.CatchPokemonAnswerUseCase;
import wlsh.project.discordgames.catchpokemon.application.dto.CatchPokemonAnswerResult;
import wlsh.project.discordgames.catchpokemon.domain.Pokemon;

@Slf4j
@Component
@RequiredArgsConstructor
public class CatchPokemonAnswerDispatcher extends ListenerAdapter {

    private final CatchPokemonAnswerUseCase catchPokemonAnswerUseCase;
    private final StatusHandler statusHandler;
    private final CatchPokemonCurrentRoundHandler catchPokemonCurrentRoundHandler;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        Message message = event.getMessage();

        if (author.isBot()) {
            return;
        }
        try {
            if (!event.getChannel().getName().equals("캐치포켓몬")) {
                return;
            }

            CatchGameId catchGameId = new CatchGameId(event.getGuild().getId(), event.getChannel().getId());
            CatchPokemonAnswerResult answer = catchPokemonAnswerUseCase.answer(
                    catchGameId,
                    new Player(author.getId(), author.getName(), message.getContentDisplay())
            );
            switch (answer.status()) {
                case CORRECT -> {
                    var content = (CatchPokemonAnswerResult.CorrectContent) answer.content();
                    sendAnswerInfo(event, author.getName(), content.pokemon());
                    statusHandler.sendStatus(event, content.status());
                    catchPokemonCurrentRoundHandler.show(catchGameId, event.getChannel());
                }
                case FINISH -> {
                    var content = (CatchPokemonAnswerResult.FinishContent) answer.content();
                    statusHandler.sendStatus(event, content.status());
                    event.getChannel().sendMessage("끝").queue();
                }
            }
        } catch (Exception e) {
            log.error("", e);
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }

    private void sendAnswerInfo(MessageReceivedEvent event, String name, Pokemon pokemon) {
        MessageEmbed embed = new EmbedBuilder()
                .setDescription("**정답** : `%s`\n".formatted(name))
                .appendDescription("`%s(%s)`\n".formatted(pokemon.getKo(), pokemon.getEn()))
                .appendDescription("`%d 세대`\n".formatted(pokemon.getGeneration()))
                .build();
        event.getChannel().sendMessageEmbeds(embed).queue();
    }
}
