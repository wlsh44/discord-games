package wlsh.project.discordgames.catchgames.catchmusic.application.dto;

import lombok.Getter;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;

@Getter
public record AnswerResult(
        Status status,
        Object content
) {

    public static AnswerResult incorrect() {
        return new AnswerResult(Status.INCORRECT, null);
    }

    public static AnswerResult correct(Music currentMusic, Music nextMusic, CatchMusicStatus status) {
        return new AnswerResult(Status.CORRECT, new CorrectContent(currentMusic, nextMusic, status));
    }

    public static AnswerResult finish() {
        return new AnswerResult(Status.FINISH, null);
    }

    public enum Status {
        INCORRECT, CORRECT, FINISH
    }

    public record CorrectContent(
            Music currentMusic,
            Music nextMusic,
            CatchMusicStatus status
    ) { }
}
