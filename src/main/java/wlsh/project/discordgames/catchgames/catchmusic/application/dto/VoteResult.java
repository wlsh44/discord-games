package wlsh.project.discordgames.catchgames.catchmusic.application.dto;

import wlsh.project.discordgames.catchgames.common.poll.domain.PollType;

public record VoteResult(
        PollType pollType,
        Object content
) {

    public static final VoteResult VOTE = new VoteResult(null, null);
}
