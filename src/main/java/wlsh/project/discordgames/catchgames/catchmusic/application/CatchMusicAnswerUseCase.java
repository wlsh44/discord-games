package wlsh.project.discordgames.catchgames.catchmusic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchgames.catchmusic.application.dto.AnswerResult;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusicRound;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchgames.common.domain.CatchGameId;
import wlsh.project.discordgames.catchgames.common.domain.Player;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatchMusicAnswerUseCase {


    private final CatchMusicRepository catchMusicRepository;
    private final CatchMusicAnswerCorrectService catchMusicAnswerCorrectService;

    public AnswerResult answer(CatchGameId catchGameId, Player player) {
        Optional<CatchMusic> catchMusicOptional = catchMusicRepository.findByCatchGameId(catchGameId);
        if (catchMusicOptional.isEmpty()) {
            return AnswerResult.incorrect();
        }
        CatchMusic catchMusic = catchMusicOptional.get();
        CatchMusicRound round = (CatchMusicRound) catchMusic.getCurrentRound();

        if (!round.answer(player)) {
            return AnswerResult.incorrect();
        }
        return catchMusicAnswerCorrectService.handleAnswerCorrect(catchMusic);
    }
}
