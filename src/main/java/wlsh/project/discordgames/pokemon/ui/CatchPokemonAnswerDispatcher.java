package wlsh.project.discordgames.pokemon.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.pokemon.application.CatchPokemonAnswerService;
import wlsh.project.discordgames.pokemon.domain.Player;

@Slf4j
@Component
@RequiredArgsConstructor
public class CatchPokemonAnswerDispatcher extends ListenerAdapter {

    private final CatchPokemonAnswerService catchPokemonAnswerService;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        Message message = event.getMessage();

        if (author.isBot()) {
            return;
        }
        try {
//            checkValidChannelState(event.getMember(), event.getGuild(), event.getChannel());
            if (!event.getChannel().getName().equals("캐치포켓몬")) {
                return;
            }

            catchPokemonAnswerService.answer(
                    event.getGuild().getId(),
                    new Player(author.getId(), author.getName(), message.getContentDisplay()),
                    event.getChannel()
            );
        } catch (Exception e) {
            log.error("", e);
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }
}
