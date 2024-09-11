package wlsh.project.discordgames.catchposkill.infra;

import org.springframework.stereotype.Repository;
import wlsh.project.discordgames.catchpokemon.domain.CatchPokemon;
import wlsh.project.discordgames.catchposkill.domain.CatchPoSkill;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class CatchPoSkillRepository {

    private final Map<CatchGameId, CatchPoSkill> data = new HashMap<>();

    public CatchPoSkill save(CatchPoSkill catchPoSkill) {
        data.put(catchPoSkill.getCatchGameId(), catchPoSkill);
        return catchPoSkill;
    }

    public Optional<CatchPoSkill> findById(CatchGameId catchGameId) {
        return Optional.ofNullable(data.getOrDefault(catchGameId, null));
    }

    public void delete(CatchGameId catchGameId) {
        data.remove(catchGameId);
    }
}
