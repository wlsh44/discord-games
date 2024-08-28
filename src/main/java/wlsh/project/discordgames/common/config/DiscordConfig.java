package wlsh.project.discordgames.common.config;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import dev.lavalink.youtube.YoutubeAudioSourceManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wlsh.project.discordgames.discord.CommandEventListener;

import java.util.EventListener;
import java.util.List;

@Configuration
public class DiscordConfig {

    private final String token;


    public DiscordConfig(
            @Value("${discord.bot.token}") String token
    ) {
        this.token = token;
    }

    @Bean
    public JDA jda(List<ListenerAdapter> listenerAdapters) {
        return JDABuilder.createDefault(token)
                .setActivity(Activity.playing("hihihi"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(listenerAdapters.toArray())
                .build();
    }

    @Bean
    public AudioPlayerManager audioPlayerManager() {
        AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();
        YoutubeAudioSourceManager youtubeAudioSourceManager = new YoutubeAudioSourceManager();
        audioPlayerManager.registerSourceManager(youtubeAudioSourceManager);
        AudioSourceManagers.registerRemoteSources(audioPlayerManager, com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager.class);
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
        return audioPlayerManager;
    }
}
