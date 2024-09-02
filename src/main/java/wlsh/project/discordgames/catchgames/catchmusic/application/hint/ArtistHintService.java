package wlsh.project.discordgames.catchgames.catchmusic.application.hint;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.catchgames.catchmusic.application.hint.dto.ArtistHintResult;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusicRound;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchgames.catchmusic.infra.spotify.ArtistInfo;
import wlsh.project.discordgames.catchgames.catchmusic.infra.spotify.SpotifySearchService;
import wlsh.project.discordgames.catchgames.common.domain.CatchGameId;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArtistHintService {

    private final CatchMusicRepository catchMusicRepository;
    private final SpotifySearchService spotifySearchService;

    public ArtistHintResult getArtistHint(CatchGameId catchGameId) {
        CatchMusic catchMusic = catchMusicRepository.findByCatchGameId(catchGameId)
                .orElseThrow(() -> new RuntimeException("없음"));
        Music music = ((CatchMusicRound)catchMusic.getCurrentRound()).getMusic();
        ArtistInfo artistInfo = spotifySearchService.searchArtistInfo(music.artist());
        return new ArtistHintResult(music.artist(), artistInfo.url());
    }
}
