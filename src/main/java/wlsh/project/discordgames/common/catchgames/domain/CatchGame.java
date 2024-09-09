package wlsh.project.discordgames.common.catchgames.domain;

import lombok.Getter;
import wlsh.project.discordgames.catchmusic.domain.FilterOption;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public abstract class CatchGame {

    protected CatchGameId catchGameId;

    protected int currentRoundNumber;

    protected int finishScore;

    protected List<Round> rounds = new ArrayList<>();
    private List<FilterOption> filterOptions = new ArrayList<>();

    public CatchGame(CatchGameId catchGameId, int currentRoundNumber, int finishScore, List<Round> rounds, List<FilterOption> filterOptions) {
        this.catchGameId = catchGameId;
        this.currentRoundNumber = currentRoundNumber;
        this.finishScore = finishScore;
        this.rounds = rounds;
        this.filterOptions = filterOptions;
    }

    public CatchGame(CatchGameId catchGameId, int currentRoundNumber, int finishScore, List<FilterOption> filterOptions) {
        this.catchGameId = catchGameId;
        this.currentRoundNumber = currentRoundNumber;
        this.finishScore = finishScore;
        this.filterOptions = filterOptions;
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
