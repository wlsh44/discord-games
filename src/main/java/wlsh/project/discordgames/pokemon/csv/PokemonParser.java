package wlsh.project.discordgames.pokemon.csv;

import wlsh.project.discordgames.pokemon.PokemonLink;
import wlsh.project.discordgames.pokemon.domain.Pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PokemonParser implements CSVParser<Pokemon> {

    @Override
    public Pokemon parse(String[] pokemonLink) {
        String index = pokemonLink[0];
        String ko = pokemonLink[1];
        String en = pokemonLink[2];
        String image = pokemonLink[3];
        String generation = pokemonLink[4];
        List<String> types = new ArrayList<>(Arrays.asList(pokemonLink).subList(5, pokemonLink.length));
        return new Pokemon(Integer.parseInt(index), ko, en, image, Integer.parseInt(generation), types);
    }
}
