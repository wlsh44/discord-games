package wlsh.project.discordgames.pokemon.infra.csv;

import wlsh.project.discordgames.pokemon.PokemonLink;

public class PokemonLinkParser implements CSVParser<PokemonLink> {

    @Override
    public PokemonLink parse(String[] pokemonLink) {
        String generation = pokemonLink[0];
        String link = pokemonLink[1];
        return new PokemonLink(generation, link);
    }
}
