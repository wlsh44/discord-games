package wlsh.project.discordgames.catchmusic.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchmusic.application.PlayMusicEvent;
import wlsh.project.discordgames.catchmusic.domain.Music;
import wlsh.project.discordgames.discord.AudioPlayerService;
import wlsh.project.discordgames.discord.util.DiscordMessageHandler;

@Component
@RequiredArgsConstructor
public class MusicPlayerHandler {

    private final AudioPlayerService audioPlayerService;
    private final DiscordMessageHandler messageHandler;

    @Async
    @EventListener
    public void playMusic(PlayMusicEvent event) {
        doCountdown(event.catchGameId().channelId());
        Music music = event.music();
        audioPlayerService.play(event.catchGameId().guildId(), music.name(), music.artist().name());
    }

    private void doCountdown(String guildId) {
        try {
            int countdown = 5;
            messageHandler.sendMessage(guildId, "5초 뒤에 다음 라운드가 시작됩니다.");
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

}
