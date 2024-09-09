package wlsh.project.discordgames.catchmusic.infra.spotify.exception;

import lombok.Getter;

@Getter
public class SpotifyTooManyRequestException extends RuntimeException {

    public static final String MESSAGE = "API rate limit exceeded";

    private final String data;
    private final int retryAfter;

    public SpotifyTooManyRequestException(String data, int retryAfter) {
        this.data = data;
        this.retryAfter = retryAfter;
    }
}
