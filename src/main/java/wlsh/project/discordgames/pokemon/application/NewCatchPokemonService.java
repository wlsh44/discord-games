package wlsh.project.discordgames.pokemon.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.pokemon.csv.CSVService;
import wlsh.project.discordgames.pokemon.csv.PokemonParser;
import wlsh.project.discordgames.pokemon.domain.CatchPokemon;
import wlsh.project.discordgames.pokemon.domain.CatchPokemonRepository;
import wlsh.project.discordgames.pokemon.domain.Pokemon;
import wlsh.project.discordgames.pokemon.domain.Round2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewCatchPokemonService {

    private final CatchPokemonRepository catchPokemonRepository;
//    private final PokemonRepository pokemonRepository;

    public void newCatchPokemon(String guildId, List<Integer> excludeList, int finishScore) {
        CSVService csvService = new CSVService();
        List<Pokemon> pokemons = csvService.readData("pokemon.csv", new PokemonParser());

        List<Pokemon> answers = Stream.generate(() -> new Random().nextInt(pokemons.size()))
                .map(pokemons::get)
                .filter(pokemon -> !excludeList.contains(pokemon.getGeneration()))
                .distinct()
                .limit(finishScore)
                .toList();

        List<Round2> rounds = new ArrayList<>();
        for (int roundNumber = 1; roundNumber <= finishScore; roundNumber++) {
            log.info(answers.get(roundNumber - 1).toString());
            rounds.add(new Round2(roundNumber, answers.get(roundNumber - 1)));
        }
        catchPokemonRepository.save(guildId, CatchPokemon.startGame(guildId, rounds, null, finishScore));

    }
}
