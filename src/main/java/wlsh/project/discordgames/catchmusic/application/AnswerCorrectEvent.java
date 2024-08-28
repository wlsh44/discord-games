package wlsh.project.discordgames.catchmusic.application;

public record AnswerCorrectEvent(
        String guildId,
        String username,
        wlsh.project.discordgames.catchmusic.domain.CatchMusic catchMusic,
        wlsh.project.discordgames.catchmusic.domain.Music currentMusic) {
}
