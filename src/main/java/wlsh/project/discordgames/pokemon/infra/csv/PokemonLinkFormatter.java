package wlsh.project.discordgames.pokemon.infra.csv;

import wlsh.project.discordgames.pokemon.infra.crawl.PokemonLink;

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
