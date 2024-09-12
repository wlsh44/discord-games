package wlsh.project.discordgames.catchpokemon.infra.csv;

import wlsh.project.discordgames.catchpokemon.infra.crawl.PokemonLink;
import wlsh.project.discordgames.common.infra.csv.CSVFormatter;

import java.util.List;

public class PokemonLinkFormatter implements CSVFormatter<PokemonLink> {

    @Override
    public List<String[]> format(List<PokemonLink> pokemonLinkList) {
        return pokemonLinkList.stream()
                .map(pokemonLink -> new String[]{
                        pokemonLink.generation(),
                        pokemonLink.link()
                })
                .toList();
    }
}
