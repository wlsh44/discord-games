package wlsh.project.discordgames.catchgames.common.catchgames.domain;

import lombok.Getter;
import wlsh.project.discordgames.pokemon.domain.Round2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public abstract class CatchGame {

    protected CatchGameId catchGameId;

    protected int currentRoundNumber;

    protected int finishScore;

    protected List<Round> rounds = new ArrayList<>();

    public CatchGame(CatchGameId catchGameId, int currentRoundNumber, int finishScore, List<Round> rounds) {
        this.catchGameId = catchGameId;
        this.currentRoundNumber = currentRoundNumber;
        this.finishScore = finishScore;
        this.rounds = rounds;
    }

    public CatchGame(CatchGameId catchGameId, int currentRoundNumber, int finishScore) {
        this.catchGameId = catchGameId;
        this.currentRoundNumber = currentRoundNumber;
        this.finishScore = finishScore;
    }

    public void updateNewRound(Round round) {
        this.rounds.add(round.createWithPrototype(currentRoundNumber++));
    }

    public Round getCurrentRound() {
        return this.rounds.get(currentRoundNumber - 1);
    }

    public boolean isFinished() {
        return rounds.stream()
                .filter(Round::isFinished)
                .collect(Collectors.groupingBy(
                        round -> round.getAnswerer().name(),
                        Collectors.mapping(
                                Round::getAnswerer,
                                Collectors.counting())
                )).values()
                .stream().anyMatch(count -> count == this.finishScore);
    }
}
