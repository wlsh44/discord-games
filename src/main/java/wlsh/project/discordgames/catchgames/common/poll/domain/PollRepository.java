package wlsh.project.discordgames.catchgames.common.poll.domain;

import java.util.Optional;

public interface PollRepository {
    Optional<Poll> findById(String pollId);

    void save(String id, Poll poll);

}
