package wlsh.project.discordgames.catchgames.catchmusic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchgames.catchmusic.application.dto.AnswerResult;
import wlsh.project.discordgames.catchgames.catchmusic.application.dto.CatchMusicStatus;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusicRound;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchgames.common.catchgames.domain.CatchGameId;

@Service
@RequiredArgsConstructor
public class CatchMusicAnswerCorrectService {

    private final ApplicationEventPublisher publisher;
    private final CatchMusicRepository catchMusicRepository;
    private final AddCatchMusicRoundService addCatchMusicRoundService;
    private final CatchMusicStatusService catchMusicStatusService;

    public AnswerResult handleAnswerCorrect(CatchMusic catchMusic) {
        CatchGameId catchGameId = catchMusic.getCatchGameId();

        CatchMusicStatus status = catchMusicStatusService.getStatus(catchMusic);

        if (catchMusic.isFinished()) {
            catchMusicRepository.delete(catchGameId);
            return AnswerResult.finish(status);
        } else {
            Music currentMusic = catchMusic.getCurrentRound().getMusic();
            addCatchMusicRoundService.addRound(catchMusic);
            Music nextMusic = catchMusic.getCurrentRound().getMusic();
            return AnswerResult.correct(currentMusic, nextMusic, status);
        }
    }
}
