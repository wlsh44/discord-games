package wlsh.project.discordgames.catchgames.catchmusic.application.dto;

import java.util.Map;

public record CatchMusicStatus(int currentRound, int finishScore, Map<String, Long> scoreBoard) {
}
