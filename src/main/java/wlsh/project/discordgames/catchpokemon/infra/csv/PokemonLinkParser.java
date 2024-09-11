package wlsh.project.discordgames.catchpokemon.infra.csv;

import wlsh.project.discordgames.catchpokemon.infra.crawl.PokemonLink;
import wlsh.project.discordgames.common.infra.csv.CSVParser;

public class PokemonLinkParser implements CSVParser<PokemonLink> {

    @Override
    public PokemonLink parse(String[] pokemonLink) {
        String generation = pokemonLink[0];
        String link = pokemonLink[1];
        return new PokemonLink(generation, link);
    }
}
