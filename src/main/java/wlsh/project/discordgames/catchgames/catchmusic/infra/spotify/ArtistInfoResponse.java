package wlsh.project.discordgames.catchgames.catchmusic.infra.spotify;

import java.util.List;

public record ArtistInfoResponse(
        ArtistList artists
) {
    public record ArtistList(
            List<Artist> items
    ) {
    }

    public record Artist(
            List<Image> images,
            String name,
            String id
    ) {}

    public record Image(
            String url
    ) {}
}
