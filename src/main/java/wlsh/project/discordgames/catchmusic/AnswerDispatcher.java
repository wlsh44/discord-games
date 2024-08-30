package wlsh.project.discordgames.catchmusic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchmusic.application.AnswerService;
import wlsh.project.discordgames.catchmusic.application.PlayService;
import wlsh.project.discordgames.catchmusic.application.dto.AnswerResult;
import wlsh.project.discordgames.catchmusic.domain.Player;

import static java.util.Objects.requireNonNull;
import static wlsh.project.discordgames.catchmusic.ChannelValidator.checkValidChannelState;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnswerDispatcher extends ListenerAdapter {

    private final AnswerService answerService;
    private final PlayService playService;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        Message message = event.getMessage();

        if (author.isBot() || event.getChannel().getName().equals("캐치포켓몬")) {
            return;
        }
        try {
            checkValidChannelState(event.getMember(), event.getGuild(), event.getChannel());

            answerService.answer(
                    event.getGuild().getId(),
                    new Player(author.getId(), author.getName(), message.getContentDisplay())
            );
        } catch (Exception e) {
            log.error("", e);
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }
}
