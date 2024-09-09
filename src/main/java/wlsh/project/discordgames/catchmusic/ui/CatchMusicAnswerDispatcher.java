package wlsh.project.discordgames.catchmusic.ui;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchmusic.application.CatchMusicAnswerUseCase;
import wlsh.project.discordgames.catchmusic.application.PlayMusicEvent;
import wlsh.project.discordgames.catchmusic.application.dto.AnswerResult;
import wlsh.project.discordgames.catchmusic.application.dto.CatchMusicStatus;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.common.catchgames.domain.Player;
import wlsh.project.discordgames.discord.AudioPlayerService;
import wlsh.project.discordgames.discord.util.DiscordMessageHandler;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CatchMusicAnswerDispatcher extends ListenerAdapter {

    private final CatchMusicAnswerUseCase catchMusicAnswerUseCase;
    private final AudioPlayerService audioPlayerService;
    private final DiscordMessageHandler messageHandler;
    private final MusicPlayerHandler musicPlayerHandler;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        Message message = event.getMessage();

        if (author.isBot() || !event.getChannel().getName().equals("캐치뮤직")) {
            return;
        }
        try {
            Guild guild = event.getGuild();
            Member member = event.getMember();
            ChannelValidator.checkValidChannelState(member, guild, event.getChannel());

            CatchGameId catchGameId = new CatchGameId(guild.getId(), event.getChannel().getId());
            AnswerResult answer = catchMusicAnswerUseCase.answer(
                    catchGameId,
                    new Player(author.getId(), author.getName(), message.getContentDisplay())
            );
            if (AnswerResult.Status.CORRECT.equals(answer.status())) {
                AnswerResult.CorrectContent content = (AnswerResult.CorrectContent) answer.content();
                sendAnswerInfo(event, guild, author.getName(), content);
                sendStatus(event, content.status());
                musicPlayerHandler.playMusic(new PlayMusicEvent(catchGameId, content.nextMusic()));
            } else if (AnswerResult.Status.FINISH.equals(answer.status())) {
                AnswerResult.FinishContent content = (AnswerResult.FinishContent) answer.content();
                sendStatus(event, content.status());
                messageHandler.sendMessage(event.getChannel(), "끝");
            }
        } catch (Exception e) {
            log.error("", e);
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }

    private void sendAnswerInfo(MessageReceivedEvent event, Guild guild, String name, AnswerResult.CorrectContent content) {
        //TODO
        //노래 끝나면 여기서 예외 남
        AudioTrackInfo audioTrackInfo = audioPlayerService.getAudioTrackInfo(guild.getId());
        messageHandler.sendEmbedMessage(
                event.getChannel(),
                null,
                "**정답** : `%s`\n".formatted(name),
                "`%s - %s`\n".formatted(content.currentMusic().name(), content.currentMusic().artist().name()),
                "**Source:** `%s\n`".formatted(audioTrackInfo.title),
                "**URL:** `%s\n`".formatted(audioTrackInfo.uri)
        );
    }

    private void sendStatus(MessageReceivedEvent event, CatchMusicStatus status) {
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
