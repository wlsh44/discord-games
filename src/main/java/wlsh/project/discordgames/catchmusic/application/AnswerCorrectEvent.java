package wlsh.project.discordgames.catchmusic.application;

import wlsh.project.discordgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchmusic.domain.Music;

public record AnswerCorrectEvent(
        String guildId,
        String username,
        CatchMusic catchMusic,
        Music currentMusic) {
}
