package wlsh.project.discordgames.pokemon.csv;

import wlsh.project.discordgames.pokemon.domain.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class PokemonFormatter implements CSVFormatter<Pokemon> {

    @Override
    public List<String[]> format(List<Pokemon> pokemonList) {
        return pokemonList.stream()
                .map(pokemon -> {
                    List<String> strings = new ArrayList<>();
                    strings.add(String.valueOf(pokemon.getIndex()));
                    strings.add(pokemon.getKo());
                    strings.add(pokemon.getEn());
                    strings.add(pokemon.getImage());
                    strings.add(String.valueOf(pokemon.getGeneration()));
                    strings.addAll(pokemon.getTypes());
                    return strings.toArray(new String[0]);
                })
                .toList();
    }
}
