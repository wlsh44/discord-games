package wlsh.project.discordgames.common.catchgames.application;

import org.springframework.stereotype.Component;
import wlsh.project.discordgames.common.catchgames.application.dto.CatchGameStatus;
import wlsh.project.discordgames.common.catchgames.domain.CatchGame;
import wlsh.project.discordgames.common.catchgames.domain.Round;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CatchGameStatusService {

    public CatchGameStatus getStatus(CatchGame catchGame) {
        List<Round> rounds = catchGame.getRounds();
        int currentRound = catchGame.getCurrentRoundNumber();
        int finishScore = catchGame.getFinishScore();
        Map<String, Long> scoreBoard = rounds.stream()
                .filter(Round::isFinished)
                .collect(Collectors.groupingBy(
                        round -> round.getAnswerer().name(),
                        Collectors.mapping(
                                Round::getAnswerer,
                                Collectors.counting())
                ));
        return new CatchGameStatus(currentRound, finishScore, scoreBoard);
    }
}
