package wlsh.project.discordgames.common.catchgames.application.dto;

import java.util.Map;

public record CatchGameStatus(int currentRound, int finishScore, Map<String, Long> scoreBoard) {
}
