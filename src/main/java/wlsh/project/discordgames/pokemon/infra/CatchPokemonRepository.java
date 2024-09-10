package wlsh.project.discordgames.pokemon.infra;

import org.springframework.stereotype.Repository;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.pokemon.domain.CatchPokemon;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class CatchPokemonRepository {

    private final Map<CatchGameId, CatchPokemon> data = new HashMap<>();

    public CatchPokemon save(CatchPokemon catchPokemon) {
        data.put(catchPokemon.getCatchGameId(), catchPokemon);
        return catchPokemon;
    }

    public Optional<CatchPokemon> findById(CatchGameId catchGameId) {
        return Optional.ofNullable(data.getOrDefault(catchGameId, null));
    }

    public void delete(CatchGameId catchGameId) {
        data.remove(catchGameId);
    }
}
