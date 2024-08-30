package wlsh.project.discordgames.pokemon.application;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.pokemon.csv.CSVService;
import wlsh.project.discordgames.pokemon.csv.PokemonParser;
import wlsh.project.discordgames.pokemon.domain.CatchPokemon;
import wlsh.project.discordgames.pokemon.domain.CatchPokemonRepository;
import wlsh.project.discordgames.pokemon.domain.Player;
import wlsh.project.discordgames.pokemon.domain.Pokemon;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class CatchPokemonAnswerService {

    private final ApplicationEventPublisher publisher;
    private final CatchPokemonRepository catchPokemonRepository;

    public void answer(String guildId, Player player, MessageChannel messageChannel) {
        Optional<CatchPokemon> catchPokemonOptional = catchPokemonRepository.get(guildId);
        if (catchPokemonOptional.isEmpty()) {
            return;
        }
        CatchPokemon catchPokemon = catchPokemonOptional.get();
        Pokemon currentPokemon = catchPokemon.getCurrentRound().getPokemon();
        boolean result = catchPokemon.answer(player);

        if (result) {
            if (catchPokemon.isMoreRoundRequired()) {
//                List<Music> musicList = mbcFM4UCrawler.crawl(Radio.valueOf(catchPokemon.getTag().content()));
//                catchPokemon.addRounds(musicList);
                CSVService csvService = new CSVService();
                List<Pokemon> pokemons = csvService.readData("pokemon.csv", new PokemonParser());
                List<Pokemon> answers = Stream.generate(() -> new Random().nextInt(pokemons.size()))
                        .map(pokemons::get)
//                        .filter(pokemon -> !excludeList.contains(pokemon.getGeneration()))
                        .distinct()
                        .limit(catchPokemon.getFinishScore())
                        .toList();
                catchPokemon.addRounds(answers);
            }
            publisher.publishEvent(new CatchPokemonAnswerCorrectEvent(guildId, player.name(), catchPokemon, currentPokemon, messageChannel));
        }
    }
}
