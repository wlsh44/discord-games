package wlsh.project.discordgames.catchgames.catchmusic.infra;

import org.springframework.stereotype.Repository;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.catchgames.common.poll.domain.Poll;
import wlsh.project.discordgames.catchgames.common.poll.domain.PollRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class CatchMusicRepositoryImpl implements CatchMusicRepository {

    private final Map<CatchGameId, CatchMusic> data = new HashMap<>();

    public void save(CatchGameId id, CatchMusic catchMusic) {
        data.put(id, catchMusic);
    }

    public CatchMusic get(CatchGameId id) {
        return data.get(id);
    }

    @Override
    public Optional<CatchMusic> findByCatchGameId(CatchGameId catchGameId) {
        return Optional.ofNullable(data.getOrDefault(catchGameId, null));
    }

    @Override
    public CatchMusic save(CatchMusic catchMusic) {
        data.put(catchMusic.getCatchGameId(), catchMusic);
        return catchMusic;
    }

    @Override
    public void delete(CatchGameId catchGameId) {
        data.remove(catchGameId);
    }
}
