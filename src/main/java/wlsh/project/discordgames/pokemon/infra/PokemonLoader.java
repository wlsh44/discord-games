package wlsh.project.discordgames.pokemon.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.pokemon.domain.CatchPokemon;
import wlsh.project.discordgames.pokemon.domain.Pokemon;
import wlsh.project.discordgames.pokemon.infra.csv.CSVService;
import wlsh.project.discordgames.pokemon.infra.csv.PokemonParser;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class PokemonLoader {

    private final CSVService csvService;

    public Pokemon loadPokemon(CatchPokemon catchPokemon) {
        List<Pokemon> pokemons = csvService.readData("pokemon.csv", new PokemonParser());
        Random random = new Random();

        return Stream.generate(() -> random.nextInt(pokemons.size()))
                .map(pokemons::get)
                .filter(randomPokemon -> catchPokemon.getFilterOptions()
                        .stream()
                        .allMatch(filterOption -> filterOption.doFilter(randomPokemon)))
                .limit(1)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("없음"));
    }
}
