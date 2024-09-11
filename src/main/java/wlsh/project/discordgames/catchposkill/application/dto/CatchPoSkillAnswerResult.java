package wlsh.project.discordgames.catchposkill.application.dto;

import wlsh.project.discordgames.catchpokemon.domain.Pokemon;
import wlsh.project.discordgames.catchposkill.domain.PoSkill;
import wlsh.project.discordgames.common.catchgames.application.dto.CatchGameStatus;

public record CatchPoSkillAnswerResult(
        Status status,
        Object content
) {

    public static final CatchPoSkillAnswerResult INCORRECT = new CatchPoSkillAnswerResult(Status.INCORRECT, null);

    public static CatchPoSkillAnswerResult correct(PoSkill poSkill, CatchGameStatus status) {
        return new CatchPoSkillAnswerResult(Status.CORRECT, new CorrectContent(poSkill, status));
    }

    public static CatchPoSkillAnswerResult finish(CatchGameStatus status) {
        return new CatchPoSkillAnswerResult(Status.FINISH, new FinishContent(status));
    }

    public enum Status {
        INCORRECT, CORRECT, FINISH
    }

    public record CorrectContent(
            PoSkill poSkill,
            CatchGameStatus status
    ) { }

    public record FinishContent(
            CatchGameStatus status
    ) {
    }

}
