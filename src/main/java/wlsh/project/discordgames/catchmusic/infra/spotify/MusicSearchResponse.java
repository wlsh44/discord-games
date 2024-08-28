package wlsh.project.discordgames.catchmusic.infra.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record MusicSearchResponse(
        Track tracks
) {

    public record Track(
            List<Item> items
    ) {}

    public record Item(
            Album album
    ) {}

    public record Album(
            String name,
            @JsonProperty("release_date")
            String releaseDate,
            List<Image> images,
            List<Artist> artists
    ) {}

    public record Image(
            String url
    ) {}

    public record Artist(
            String id,
            String name
    ) {}
}
