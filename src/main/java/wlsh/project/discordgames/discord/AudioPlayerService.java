package wlsh.project.discordgames.discord;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Guild;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AudioPlayerService {

    private final Map<String, GuildMusicManager> guildMusicManagers = new HashMap<>();
    private final GuildRepository guildRepository;
    private final AudioPlayerManager audioPlayerManager;

    public GuildMusicManager getGuildMusicManager(String guildId) {
        return guildMusicManagers.computeIfAbsent(guildId, (id) -> {
            AudioPlayer player = audioPlayerManager.createPlayer();
            Guild guild = guildRepository.get(id);
            return new GuildMusicManager(player, guild);
        });
    }

    public void play(String guildId, String name, String artist) {
        GuildMusicManager guildMusicManager = getGuildMusicManager(guildId);
        String trackURL = StringUtils.hasText(artist) ? "ytsearch:%s - %s".formatted(name, artist) : "ytsearch:%s".formatted(name);
        audioPlayerManager.loadItemOrdered(guildMusicManager, trackURL, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                guildMusicManager.play(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                guildMusicManager.play(playlist.getTracks().get(0));
            }

            @Override
            public void noMatches() {}

            @Override
            public void loadFailed(FriendlyException exception) {}
        });
    }

    public AudioTrackInfo getAudioTrackInfo(String guildId) {
        GuildMusicManager guildMusicManager = getGuildMusicManager(guildId);
        return guildMusicManager.getPlayingTrackInfo();
    }
}