package wlsh.project.discordgames.catchgames.catchmusic.domain;

import org.springframework.stereotype.Repository;
import wlsh.project.discordgames.catchgames.common.domain.CatchGameId;

import java.util.Optional;

@Repository
public interface CatchMusicRepository {
    Optional<CatchMusic> findByCatchGameId(CatchGameId catchGameId);
    CatchMusic save(CatchMusic catchMusic);

    void delete(CatchGameId catchGameId);
}
