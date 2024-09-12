package wlsh.project.discordgames.catchmusic.infra.spotify;

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
