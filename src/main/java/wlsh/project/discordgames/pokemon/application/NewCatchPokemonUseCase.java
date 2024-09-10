package wlsh.project.discordgames.pokemon.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.pokemon.domain.FilterOption;
import wlsh.project.discordgames.pokemon.domain.GenerationFilter;
import wlsh.project.discordgames.pokemon.domain.CatchPokemon;
import wlsh.project.discordgames.pokemon.infra.CatchPokemonRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewCatchPokemonUseCase {

    private final CatchPokemonRepository catchPokemonRepository;
    private final AddCatchPokemonRoundService addCatchPokemonRoundService;

    public void newCatchPokemon(CatchGameId catchGameId, List<Integer> excludeList, int finishScore) {
        List<FilterOption> filters = List.of(
                new GenerationFilter(excludeList)
        );
        CatchPokemon catchPokemon = catchPokemonRepository.save(CatchPokemon.startGame(catchGameId, finishScore, filters));
        addCatchPokemonRoundService.addRound(catchPokemon);
    }
}
