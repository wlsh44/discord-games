package wlsh.project.discordgames.catchmusic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchmusic.application.dto.CatchMusicStatus;
import wlsh.project.discordgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.common.catchgames.domain.Round;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CatchMusicStatusService {

    public CatchMusicStatus getStatus(CatchMusic catchMusic) {
        List<Round> rounds = catchMusic.getRounds();
        int currentRound = catchMusic.getCurrentRoundNumber();
        int finishScore = catchMusic.getFinishScore();
        Map<String, Long> scoreBoard = rounds.stream()
                .filter(Round::isFinished)
                .collect(Collectors.groupingBy(
                        round -> round.getAnswerer().name(),
                        Collectors.mapping(
                                Round::getAnswerer,
                                Collectors.counting())
                ));
        return new CatchMusicStatus(currentRound, finishScore, scoreBoard);
    }
}
