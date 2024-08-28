package wlsh.project.discordgames.catchmusic;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class CatchMusicConfig {

    private final String catchMusicTextChannel;
    private final String catchMusicVoiceChannel;

    public CatchMusicConfig(
            @Value("${discord.catch_music.text_channel}") String catchMusicTextChannel,
            @Value("${discord.catch_music.voice_channel}") String catchMusicVoiceChannel
    ) {
        this.catchMusicTextChannel = catchMusicTextChannel;
        this.catchMusicVoiceChannel = catchMusicVoiceChannel;
    }
}
