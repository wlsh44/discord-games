package wlsh.project.discordgames.catchmusic.application.hint;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.catchmusic.application.hint.dto.ArtistHintResult;
import wlsh.project.discordgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchmusic.domain.Round;
import wlsh.project.discordgames.catchmusic.infra.spotify.ArtistInfo;
import wlsh.project.discordgames.catchmusic.infra.spotify.SpotifySearchService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArtistHintService {

    private final CatchMusicRepository catchMusicRepository;
    private final SpotifySearchService spotifySearchService;

    public ArtistHintResult getArtistHint(String guildId) {
        CatchMusic catchMusic = catchMusicRepository.findByGuildId(guildId)
                .orElseThrow(() -> new RuntimeException("없음"));
        Round round = catchMusic.getCurrentRound();
        ArtistInfo artistInfo = spotifySearchService.searchArtistInfo(round.getMusic().artist());
        return new ArtistHintResult(round.getMusic().artist(), artistInfo.url());
    }
}
