package wlsh.project.discordgames.catchgames.catchmusic.infra.spotify;

public record SpotifyErrorResponse(
        ErrorDto error
) {

    public record ErrorDto(
            int status,
            String message
    ) {
    }
}
