package wlsh.project.discordgames.common.ui;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.common.catchgames.application.dto.CatchGameStatus;
import wlsh.project.discordgames.discord.util.DiscordMessageHandler;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StatusHandler {

    private final DiscordMessageHandler messageHandler;

    public void sendStatus(MessageReceivedEvent event, CatchGameStatus status) {
        String roundStatus = "`%d 라운드`".formatted(status.currentRound());
        String finishScore = "`%d 점`".formatted(status.finishScore());
        String scoreBoard = status.scoreBoard().entrySet().stream()
                .map(catchMusicStatus -> "`%s : %d점`".formatted(catchMusicStatus.getKey(), catchMusicStatus.getValue()))
                .collect(Collectors.joining("\n"));
        messageHandler.sendEmbedMessage(
                event.getChannel(),
                "통계",
                "**Round** : %s\n".formatted(roundStatus),
                "**목표 점수** : %s\n\n".formatted(finishScore),
                "**점수**\n",
                scoreBoard
        );
    }
}
