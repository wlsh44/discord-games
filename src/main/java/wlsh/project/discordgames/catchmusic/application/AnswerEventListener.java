package wlsh.project.discordgames.catchmusic.application;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchmusic.domain.Music;
import wlsh.project.discordgames.discord.AudioPlayerService;
import wlsh.project.discordgames.discord.ChannelRepository;
import wlsh.project.discordgames.discord.util.DiscordMessageHandler;

@Component
@RequiredArgsConstructor
public class AnswerEventListener {

    private final CatchMusicStatusService catchMusicStatusService;
    private final DiscordMessageHandler messageHandler;
    private final AudioPlayerService audioPlayerService;
    private final CatchMusicRepository catchMusicRepository;

    @EventListener
    public void handleAnswerCorrect(AnswerCorrectEvent event) {
        String guildId = event.guildId();
        AudioTrackInfo audioTrackInfo = audioPlayerService.getAudioTrackInfo(event.guildId());
        messageHandler.sendEmbedMessage(
                guildId,
                null,
                "**정답** : `%s`\n".formatted(event.username()),
                "`%s - %s`\n".formatted(event.currentMusic().name(), event.currentMusic().artist()),
                "**Source:** `%s\n`".formatted(audioTrackInfo.title),
                "**URL:** `%s\n`".formatted(audioTrackInfo.uri)
        );
        catchMusicStatusService.sendStatus(guildId);
        if (event.catchMusic().isFinished()) {
            handleCatchMusicFinished(guildId);
            catchMusicRepository.delete(event.catchMusic());
            return;
        }
        doCountdown(guildId);
        Music music = event.catchMusic().getCurrentRound().getMusic();
        audioPlayerService.play(guildId, music.name(), music.artist());
    }

    private void doCountdown(String guildId) {
        try {
            int countdown = 5;
            messageHandler.sendMessage(guildId, "5초 뒤에 다음 라운드가 시작됩니다.");
            Thread.sleep(1000);
            String id = messageHandler.sendMessageWithId(guildId, String.valueOf(countdown--));
            while (countdown > 0) {
                Thread.sleep(1000);
                messageHandler.editMessage(guildId, id, String.valueOf(countdown--));
            }
            messageHandler.editMessage(guildId, id, "시작!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleCatchMusicFinished(String guildId) {
        messageHandler.sendMessage(guildId, "끝");
    }
}
