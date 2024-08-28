package wlsh.project.discordgames.catchmusic.infra.spotify;

public record MusicInfo(
        String musicName,
        String albumUrl,
        String albumName,
        String releaseDate
) {
}
