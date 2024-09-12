package wlsh.project.discordgames.catchmusic.application.hint;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.catchmusic.application.hint.dto.ArtistHintResult;
import wlsh.project.discordgames.catchmusic.domain.Artist;
import wlsh.project.discordgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchmusic.infra.spotify.SpotifySearchService;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArtistHintService {

    private final CatchMusicRepository catchMusicRepository;

    public ArtistHintResult getArtistHint(CatchGameId catchGameId) {
        CatchMusic catchMusic = catchMusicRepository.findByCatchGameId(catchGameId)
                .orElseThrow(() -> new RuntimeException("없음"));
        Music music = catchMusic.getCurrentRound().getMusic();
        Artist artist = music.artist();
        return new ArtistHintResult(artist.name(), artist.url());
    }
}
