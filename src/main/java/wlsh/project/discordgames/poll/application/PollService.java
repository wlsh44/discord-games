package wlsh.project.discordgames.poll.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.catchmusic.application.SkipService;
import wlsh.project.discordgames.catchmusic.application.StopService;
import wlsh.project.discordgames.catchmusic.application.hint.AlbumHintService;
import wlsh.project.discordgames.catchmusic.application.hint.ArtistHintService;
import wlsh.project.discordgames.catchmusic.application.hint.MusicNameHintService;
import wlsh.project.discordgames.poll.domain.Poll;
import wlsh.project.discordgames.poll.domain.PollRepository;
import wlsh.project.discordgames.poll.domain.Voter;

@Service
@Transactional
@RequiredArgsConstructor
public class PollService {

    private final PollRepository pollRepository;
    private final AlbumHintService albumHintService;
    private final MusicNameHintService musicNameHintService;
    private final ArtistHintService artistHintService;
    private final SkipService skipService;
    private final StopService stopService;

    public void vote(String guildId, Voter voter) {
        Poll poll = pollRepository.findById(guildId)
                .orElseThrow(() -> new RuntimeException("없음"));

        poll.vote(voter);
        if (poll.isMajority()) {
            switch (poll.getCategory()) {
                case ALBUM_HINT -> {
                    albumHintService.getAlbumHint(guildId);
                }
                case ARTIST_HINT -> {
                    artistHintService.getArtistHint(guildId);
                }
                case TITLE_HINT -> {
                    musicNameHintService.getMusicNameHint(guildId);
                }
                case SKIP -> {
                    skipService.skip(guildId);
                }
                case STOP -> {
                    stopService.stop(guildId);
                }default -> new RuntimeException("예외");
            }
        }
    }
}
