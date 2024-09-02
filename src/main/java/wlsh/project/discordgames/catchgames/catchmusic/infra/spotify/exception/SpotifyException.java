package wlsh.project.discordgames.catchgames.catchmusic.infra.spotify.exception;

import lombok.Getter;

@Getter
public class SpotifyException extends RuntimeException {

    public SpotifyException(int status, String message) {
        super("status: %d, message: %s".formatted(status, message));
    }
}
