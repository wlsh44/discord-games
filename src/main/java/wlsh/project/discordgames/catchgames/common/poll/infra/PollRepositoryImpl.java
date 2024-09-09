package wlsh.project.discordgames.catchgames.common.poll.infra;

import org.springframework.stereotype.Repository;
import wlsh.project.discordgames.catchgames.common.poll.domain.Poll;
import wlsh.project.discordgames.catchgames.common.poll.domain.PollRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class PollRepositoryImpl implements PollRepository {

    private final Map<String, Poll> data = new HashMap<>();

    public void save(String id, Poll poll) {
        data.put(id, poll);
    }

    public Poll get(String id) {
        return data.get(id);
    }

    @Override
    public Optional<Poll> findById(String id) {
        return Optional.ofNullable(data.getOrDefault(id, null));
    }
}
