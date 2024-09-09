package wlsh.project.discordgames.catchgames.catchmusic.application.dto;

import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;

public record SkipResponse(
        String answer,
        String information
) {
}
