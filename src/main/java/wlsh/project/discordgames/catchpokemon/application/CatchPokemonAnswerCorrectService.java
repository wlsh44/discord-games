package wlsh.project.discordgames.catchpokemon.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.common.catchgames.application.CatchGameStatusService;
import wlsh.project.discordgames.common.catchgames.application.dto.CatchGameStatus;
import wlsh.project.discordgames.catchpokemon.application.dto.CatchPokemonAnswerResult;
import wlsh.project.discordgames.catchpokemon.domain.CatchPokemon;
import wlsh.project.discordgames.catchpokemon.domain.Pokemon;
import wlsh.project.discordgames.catchpokemon.infra.CatchPokemonRepository;

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
