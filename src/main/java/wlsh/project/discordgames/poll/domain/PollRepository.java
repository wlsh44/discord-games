package wlsh.project.discordgames.poll.domain;

import java.util.Optional;

public interface PollRepository {
    Optional<Poll> findById(String guildId);

    void save(String id, Poll poll);

}
