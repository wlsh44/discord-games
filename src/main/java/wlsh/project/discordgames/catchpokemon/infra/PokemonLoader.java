package wlsh.project.discordgames.catchpokemon.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchpokemon.domain.CatchPokemon;
import wlsh.project.discordgames.catchpokemon.domain.Pokemon;
import wlsh.project.discordgames.common.infra.csv.CSVService;
import wlsh.project.discordgames.catchpokemon.infra.csv.PokemonParser;

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
                .filter(randomPokemon -> catchPokemon.getPokemonFilterOptions()
                        .stream()
                        .allMatch(filterOption -> filterOption.doFilter(randomPokemon)))
                .limit(1)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("없음"));
    }
}
