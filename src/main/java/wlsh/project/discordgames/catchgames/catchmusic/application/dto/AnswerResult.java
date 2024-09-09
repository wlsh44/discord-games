package wlsh.project.discordgames.catchgames.catchmusic.application.dto;

import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;

public record AnswerResult(
        Status status,
        Object content
) {

    public static final AnswerResult INCORRECT = new AnswerResult(Status.INCORRECT, null);

    public static AnswerResult correct(Music currentMusic, Music nextMusic, CatchMusicStatus status) {
        return new AnswerResult(Status.CORRECT, new CorrectContent(currentMusic, nextMusic, status));
    }

    public static AnswerResult finish(CatchMusicStatus status) {
        return new AnswerResult(Status.FINISH, new FinishContent(status));
    }

    public enum Status {
        INCORRECT, CORRECT, FINISH
    }

    public record CorrectContent(
            Music currentMusic,
            Music nextMusic,
            CatchMusicStatus status
    ) { }

    public record FinishContent(
            CatchMusicStatus status
    ) { }
}
