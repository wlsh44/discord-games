package wlsh.project.discordgames.catchmusic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchmusic.application.dto.CatchMusicAnswerResult;
import wlsh.project.discordgames.catchmusic.application.dto.CatchMusicStatus;
import wlsh.project.discordgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchmusic.domain.Music;
import wlsh.project.discordgames.common.catchgames.application.CatchGameStatusService;
import wlsh.project.discordgames.common.catchgames.application.dto.CatchGameStatus;

@Service
@RequiredArgsConstructor
public class CatchMusicAnswerCorrectService {

    private final CatchMusicRepository catchMusicRepository;
    private final AddCatchMusicRoundService addCatchMusicRoundService;
    private final CatchGameStatusService catchGameStatusService;

    public CatchMusicAnswerResult handleAnswerCorrect(CatchMusic catchMusic) {
        CatchGameStatus status = catchGameStatusService.getStatus(catchMusic);

        if (catchMusic.isFinished()) {
            catchMusicRepository.delete(catchMusic.getCatchGameId());
            return CatchMusicAnswerResult.finish(status);
        } else {
            Music currentMusic = catchMusic.getCurrentRound().getMusic();
            addCatchMusicRoundService.addRound(catchMusic);
            Music nextMusic = catchMusic.getCurrentRound().getMusic();
            return CatchMusicAnswerResult.correct(currentMusic, nextMusic, status);
        }
    }
}
