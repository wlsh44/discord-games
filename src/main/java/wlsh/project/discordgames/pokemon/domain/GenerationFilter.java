package wlsh.project.discordgames.pokemon.domain;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GenerationFilter implements FilterOption {

    private final List<Integer> generations;

    @Override
    public boolean doFilter(Pokemon pokemon) {
        return !generations.contains(pokemon.getGeneration());
    }
}
