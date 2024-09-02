package wlsh.project.discordgames.catchgames.catchmusic.application.hint;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.catchgames.catchmusic.application.hint.dto.AlbumHintResult;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusicRound;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchgames.catchmusic.infra.spotify.MusicInfo;
import wlsh.project.discordgames.catchgames.catchmusic.infra.spotify.SpotifySearchService;
import wlsh.project.discordgames.catchgames.common.domain.CatchGameId;

@Service
@RequiredArgsConstructor
public class AlbumHintService {

    private final CatchMusicRepository catchMusicRepository;
    private final SpotifySearchService spotifySearchService;

    public AlbumHintResult getAlbumHint(CatchGameId catchGameId) {
        CatchMusic catchMusic = catchMusicRepository.findByCatchGameId(catchGameId)
                .orElseThrow(() -> new RuntimeException("없음"));
        Music music = ((CatchMusicRound)catchMusic.getCurrentRound()).getMusic();
        MusicInfo musicInfo = spotifySearchService.searchMusicInfo(music);
        return new AlbumHintResult(musicInfo.albumUrl(), musicInfo.albumName(), musicInfo.releaseDate());
    }
}
