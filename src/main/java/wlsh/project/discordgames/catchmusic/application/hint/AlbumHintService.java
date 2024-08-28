package wlsh.project.discordgames.catchmusic.application.hint;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.catchmusic.application.hint.dto.AlbumHintResult;
import wlsh.project.discordgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchmusic.domain.Round;
import wlsh.project.discordgames.catchmusic.infra.spotify.MusicInfo;
import wlsh.project.discordgames.catchmusic.infra.spotify.SpotifySearchService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlbumHintService {

    private final CatchMusicRepository catchMusicRepository;
    private final SpotifySearchService spotifySearchService;

    public AlbumHintResult getAlbumHint(String guildId) {
        CatchMusic catchMusic = catchMusicRepository.findByGuildId(guildId)
                .orElseThrow(() -> new RuntimeException("없음"));
        Round round = catchMusic.getCurrentRound();
        MusicInfo musicInfo = spotifySearchService.searchMusicInfo(round.getMusic());
        return new AlbumHintResult(musicInfo.albumUrl(), musicInfo.albumName(), musicInfo.releaseDate());
    }
}
