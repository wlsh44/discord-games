package wlsh.project.discordgames.catchpokemon.domain;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GenerationFilter implements PokemonFilterOption {

    private final List<Integer> generations;

    @Override
    public boolean doFilter(Pokemon pokemon) {
        return !generations.contains(pokemon.getGeneration());
    }
}
