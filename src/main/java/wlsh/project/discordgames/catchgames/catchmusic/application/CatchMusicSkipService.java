package wlsh.project.discordgames.catchgames.catchmusic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.catchgames.catchmusic.application.dto.SkipResponse;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchgames.common.catchgames.domain.CatchGameId;

@Service
@Transactional
@RequiredArgsConstructor
public class CatchMusicSkipService {

    private final ApplicationEventPublisher publisher;
    private final CatchMusicRepository catchMusicRepository;
    private final AddCatchMusicRoundService addCatchMusicRoundService;

    public SkipResponse skip(CatchGameId catchGameId) {
        CatchMusic catchMusic = catchMusicRepository.findByCatchGameId(catchGameId)
                .orElseThrow(() -> new RuntimeException("없음"));
        Music skippedMusic = catchMusic.getCurrentRound().getMusic();
        addCatchMusicRoundService.addRound(catchMusic);
        Music nextMusic = catchMusic.getCurrentRound().getMusic();
        publisher.publishEvent(new PlayMusicEvent(catchGameId, nextMusic));
        return new SkipResponse(skippedMusic.name(), skippedMusic.artist().name());
    }
}
