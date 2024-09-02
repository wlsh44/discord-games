package wlsh.project.discordgames.catchgames.catchmusic.application;

import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchgames.common.domain.CatchGameId;

public record PlayMusicEvent(
        CatchGameId catchGameId, Music music
) {
}
