package wlsh.project.discordgames.discord;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.entities.Guild;

public class GuildMusicManager {

    private final TrackScheduler trackScheduler;
    private final AudioForwarder audioForwarder;

    public GuildMusicManager(AudioPlayer player, Guild guild) {
        trackScheduler = new TrackScheduler(player);
        player.addListener(trackScheduler);
        audioForwarder = new AudioForwarder(player, guild);
        guild.getAudioManager().setSendingHandler(audioForwarder);
    }

    public void play(AudioTrack track) {
        trackScheduler.start(track);
    }

    public boolean isPlaying() {
        return trackScheduler.isPlaying();
    }

    public AudioTrackInfo getPlayingTrackInfo() {
        return trackScheduler.getPlayingTrackInfo();
    }
}