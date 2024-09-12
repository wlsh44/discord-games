package wlsh.project.discordgames.catchmusic.application.dto;

import wlsh.project.discordgames.common.poll.domain.PollType;

public record VoteResult(
        PollType pollType,
        Object content
) {

    public static final VoteResult VOTE = new VoteResult(null, null);
}
