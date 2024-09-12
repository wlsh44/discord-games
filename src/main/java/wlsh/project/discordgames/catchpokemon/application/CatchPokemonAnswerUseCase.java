package wlsh.project.discordgames.catchpokemon.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.common.catchgames.domain.Player;
import wlsh.project.discordgames.catchpokemon.application.dto.CatchPokemonAnswerResult;
import wlsh.project.discordgames.catchpokemon.domain.CatchPokemonRound;
import wlsh.project.discordgames.catchpokemon.domain.CatchPokemon;
import wlsh.project.discordgames.catchpokemon.infra.CatchPokemonRepository;

import java.util.Optional;

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
