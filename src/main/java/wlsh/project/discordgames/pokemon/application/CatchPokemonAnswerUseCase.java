package wlsh.project.discordgames.pokemon.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.common.catchgames.domain.Player;
import wlsh.project.discordgames.pokemon.application.dto.CatchPokemonAnswerResult;
import wlsh.project.discordgames.pokemon.domain.CatchPokemonRound;
import wlsh.project.discordgames.pokemon.infra.csv.CSVService;
import wlsh.project.discordgames.pokemon.infra.csv.PokemonParser;
import wlsh.project.discordgames.pokemon.domain.CatchPokemon;
import wlsh.project.discordgames.pokemon.infra.CatchPokemonRepository;
import wlsh.project.discordgames.pokemon.domain.Pokemon;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class CatchPokemonAnswerUseCase {

    private final CatchPokemonRepository catchPokemonRepository;
    private final CatchPokemonAnswerCorrectService catchPokemonAnswerCorrectService;

    public CatchPokemonAnswerResult answer(CatchGameId catchGameId, Player player) {
        Optional<CatchPokemon> catchPokemonOptional = catchPokemonRepository.findById(catchGameId);
        if (catchPokemonOptional.isEmpty()) {
            return CatchPokemonAnswerResult.INCORRECT;
        }
        CatchPokemon catchPokemon = catchPokemonOptional.get();
        CatchPokemonRound round = catchPokemon.getCurrentRound();

        if (!round.answer(player)) {
            return CatchPokemonAnswerResult.INCORRECT;
        }
        return catchPokemonAnswerCorrectService.handleAnswerCorrect(catchPokemon);
    }
}
