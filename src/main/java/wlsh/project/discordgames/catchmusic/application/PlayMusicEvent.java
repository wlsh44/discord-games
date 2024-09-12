package wlsh.project.discordgames.catchmusic.application;

import wlsh.project.discordgames.catchmusic.domain.Music;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;

public record PlayMusicEvent(
        CatchGameId catchGameId, Music music
) {
}
