package wlsh.project.discordgames.catchposkill.domain;

import lombok.Getter;
import wlsh.project.discordgames.common.catchgames.domain.CatchGame;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;


@Getter
public class CatchPoSkill extends CatchGame {

    public CatchPoSkill(CatchGameId catchGameId, int currentRoundNumber, int finishScore) {
        super(catchGameId, currentRoundNumber, finishScore);
    }

    public static CatchPoSkill startGame(CatchGameId catchGameId, int finishScore) {
        return new CatchPoSkill(catchGameId, 0, finishScore);
    }

    public CatchPoSkillRound getCurrentRound() {
        return (CatchPoSkillRound) rounds.get(currentRoundNumber - 1);
    }

}
