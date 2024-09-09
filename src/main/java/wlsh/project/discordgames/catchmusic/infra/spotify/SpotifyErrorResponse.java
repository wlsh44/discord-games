package wlsh.project.discordgames.catchmusic.infra.spotify;

public record SpotifyErrorResponse(
        ErrorDto error
) {

    public record ErrorDto(
            int status,
            String message
    ) {
    }
}
