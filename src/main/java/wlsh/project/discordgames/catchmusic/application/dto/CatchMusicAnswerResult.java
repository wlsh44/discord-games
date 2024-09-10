package wlsh.project.discordgames.catchmusic.application.dto;

import wlsh.project.discordgames.catchmusic.domain.Music;
import wlsh.project.discordgames.common.catchgames.application.dto.CatchGameStatus;

public record CatchMusicAnswerResult(
        Status status,
        Object content
) {

    public static final CatchMusicAnswerResult INCORRECT = new CatchMusicAnswerResult(Status.INCORRECT, null);

    public static CatchMusicAnswerResult correct(Music currentMusic, Music nextMusic, CatchGameStatus status) {
        return new CatchMusicAnswerResult(Status.CORRECT, new CorrectContent(currentMusic, nextMusic, status));
    }

    public static CatchMusicAnswerResult finish(CatchGameStatus status) {
        return new CatchMusicAnswerResult(Status.FINISH, new FinishContent(status));
    }

    public enum Status {
        INCORRECT, CORRECT, FINISH
    }

    public record CorrectContent(
            Music currentMusic,
            Music nextMusic,
            CatchGameStatus status
    ) { }

    public record FinishContent(
            CatchGameStatus status
    ) { }
}
