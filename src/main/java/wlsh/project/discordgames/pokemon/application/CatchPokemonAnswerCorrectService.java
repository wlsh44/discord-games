package wlsh.project.discordgames.pokemon.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.common.catchgames.application.CatchGameStatusService;
import wlsh.project.discordgames.common.catchgames.application.dto.CatchGameStatus;
import wlsh.project.discordgames.pokemon.application.dto.CatchPokemonAnswerResult;
import wlsh.project.discordgames.pokemon.domain.CatchPokemon;
import wlsh.project.discordgames.pokemon.domain.Pokemon;
import wlsh.project.discordgames.pokemon.infra.CatchPokemonRepository;

@Service
@RequiredArgsConstructor
public class CatchPokemonAnswerCorrectService {

    private final CatchPokemonRepository catchPokemonRepository;
    private final AddCatchPokemonRoundService addCatchPokemonRoundService;
    private final CatchGameStatusService catchGameStatusService;

    public CatchPokemonAnswerResult handleAnswerCorrect(CatchPokemon catchPokemon) {
        CatchGameStatus status = catchGameStatusService.getStatus(catchPokemon);

        if (catchPokemon.isFinished()) {
            catchPokemonRepository.delete(catchPokemon.getCatchGameId());
            return CatchPokemonAnswerResult.finish(status);
        } else {
            Pokemon pokemon = catchPokemon.getCurrentRound().getPokemon();
            addCatchPokemonRoundService.addRound(catchPokemon);
            return CatchPokemonAnswerResult.correct(pokemon, status);
        }
    }

}
