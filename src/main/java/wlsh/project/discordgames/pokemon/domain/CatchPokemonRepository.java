package wlsh.project.discordgames.pokemon.domain;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class CatchPokemonRepository {

    private final Map<String, CatchPokemon> data = new HashMap<>();

    public void save(String guildId, CatchPokemon catchPokemon) {
        data.put(guildId, catchPokemon);
    }

    public Optional<CatchPokemon> get(String guildId) {
        return Optional.ofNullable(data.getOrDefault(guildId, null));
    }

    public void delete(String guildId) {
        data.remove(guildId);
    }
}
