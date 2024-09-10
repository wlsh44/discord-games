package wlsh.project.discordgames.pokemon.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.pokemon.domain.CatchPokemon;
import wlsh.project.discordgames.pokemon.domain.CatchPokemonRound;
import wlsh.project.discordgames.pokemon.domain.Pokemon;
import wlsh.project.discordgames.pokemon.infra.PokemonLoader;

@Service
@RequiredArgsConstructor
public class AddCatchPokemonRoundService {

    private final PokemonLoader pokemonLoader;

    public void addRound(CatchPokemon catchPokemon) {
        Pokemon pokemon = pokemonLoader.loadPokemon(catchPokemon);
        catchPokemon.updateNewRound(CatchPokemonRound.prototype(pokemon));
    }
}
