package wlsh.project.discordgames.catchgames.catchmusic.infra.spotify;

public record MusicInfo(
        String musicName,
        String artistName,
        String artistUrl,
        String albumUrl,
        String albumName,
        String releaseDate,
        int popularity
) {
}
