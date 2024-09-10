package wlsh.project.discordgames.catchmusic.domain;

import lombok.Getter;
import wlsh.project.discordgames.catchmusic.infra.crawler.Radio;
import wlsh.project.discordgames.common.catchgames.domain.CatchGame;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CatchMusic extends CatchGame {

    private Radio radio;
    private List<FilterOption> filterOptions;

    public CatchMusic(CatchGameId catchGameId, int currentRoundNumber, List<FilterOption> filterOptions, Radio radio, int finishScore) {
        super(catchGameId, currentRoundNumber, finishScore);
        this.radio = radio;
        this.filterOptions = filterOptions;
    }

    public static CatchMusic startGame(CatchGameId catchGameId, int finishScore, List<FilterOption> filterOptions, Radio radio) {
        return new CatchMusic(catchGameId, 0, filterOptions, radio, finishScore);
    }

    public CatchMusicRound getCurrentRound() {
        return (CatchMusicRound) rounds.get(currentRoundNumber - 1);
    }
}
