package wlsh.project.discordgames.catchmusic.application;

public record CatchMusicFinishedEvent(
        String guildId,
        String username
) {
}
