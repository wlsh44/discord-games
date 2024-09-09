package wlsh.project.discordgames.catchgames.catchmusic.application.hint;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchgames.catchmusic.application.hint.dto.AlbumHintResult;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Album;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchgames.catchmusic.infra.spotify.SpotifySearchService;
import wlsh.project.discordgames.catchgames.common.catchgames.domain.CatchGameId;

@Service
@RequiredArgsConstructor
public class AlbumHintService {

    private final CatchMusicRepository catchMusicRepository;
    private final SpotifySearchService spotifySearchService;

    public AlbumHintResult getAlbumHint(CatchGameId catchGameId) {
        CatchMusic catchMusic = catchMusicRepository.findByCatchGameId(catchGameId)
                .orElseThrow(() -> new RuntimeException("없음"));
        Music music = catchMusic.getCurrentRound().getMusic();
//        MusicInfo musicInfo = spotifySearchService.searchMusicInfo(music);
        Album album = music.album();
        return new AlbumHintResult(album.url(), album.name(), music.releaseDate());
    }
}
