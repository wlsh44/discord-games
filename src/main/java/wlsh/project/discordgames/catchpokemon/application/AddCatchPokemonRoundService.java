package wlsh.project.discordgames.catchpokemon.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchpokemon.domain.CatchPokemon;
import wlsh.project.discordgames.catchpokemon.domain.CatchPokemonRound;
import wlsh.project.discordgames.catchpokemon.domain.Pokemon;
import wlsh.project.discordgames.catchpokemon.infra.PokemonLoader;

@Service
@RequiredArgsConstructor
public class AddCatchPokemonRoundService {

    private final PokemonLoader pokemonLoader;

    public void addRound(CatchPokemon catchPokemon) {
        Pokemon pokemon = pokemonLoader.loadPokemon(catchPokemon);
        catchPokemon.updateNewRound(CatchPokemonRound.prototype(pokemon));
    }
}
