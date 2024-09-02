package wlsh.project.discordgames.catchgames.common.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class CatchGame {

    private CatchGameId catchGameId;

    private int currentRoundNumber;

    private int finishScore;

    private List<Round> rounds = new ArrayList<>();

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
        int finishedScore = (int) rounds.stream()
                .filter(Round::isFinished)
                .count();
        return this.finishScore == finishedScore;
    }
}
