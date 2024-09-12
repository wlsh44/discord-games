package wlsh.project.discordgames.catchmusic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchmusic.application.dto.CatchMusicAnswerResult;
import wlsh.project.discordgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.common.catchgames.domain.Player;
import wlsh.project.discordgames.common.catchgames.domain.Round;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatchMusicAnswerUseCase {

    private final CatchMusicRepository catchMusicRepository;
    private final CatchMusicAnswerCorrectService catchMusicAnswerCorrectService;

    public CatchMusicAnswerResult answer(CatchGameId catchGameId, Player player) {
        Optional<CatchMusic> catchMusicOptional = catchMusicRepository.findByCatchGameId(catchGameId);
        if (catchMusicOptional.isEmpty()) {
            return CatchMusicAnswerResult.INCORRECT;
        }
        CatchMusic catchMusic = catchMusicOptional.get();
        Round round = catchMusic.getCurrentRound();

        if (!round.answer(player)) {
            return CatchMusicAnswerResult.INCORRECT;
        }
        return catchMusicAnswerCorrectService.handleAnswerCorrect(catchMusic);
    }
}
