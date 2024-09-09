package wlsh.project.discordgames.catchmusic.infra.spotify.exception;

import lombok.Getter;

@Getter
public class SpotifyTokenExpiredException extends RuntimeException {

    public static final String MESSAGE = "The access token expired";

    private final String data;

    public SpotifyTokenExpiredException(String data) {
        this.data = data;
    }
}
