package wlsh.project.discordgames.catchpokemon.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.catchpokemon.application.dto.CatchPokemonSkipResponse;
import wlsh.project.discordgames.catchpokemon.domain.CatchPokemon;
import wlsh.project.discordgames.catchpokemon.domain.Pokemon;
import wlsh.project.discordgames.catchpokemon.infra.CatchPokemonRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CatchPokemonSkipService {

    private final CatchPokemonRepository catchPokemonRepository;
    private final AddCatchPokemonRoundService addCatchPokemonRoundService;

    public CatchPokemonSkipResponse skip(CatchGameId catchGameId) {
        CatchPokemon catchPokemon = catchPokemonRepository.findById(catchGameId)
                .orElseThrow(() -> new RuntimeException("없음"));
        Pokemon pokemon = catchPokemon.getCurrentRound().getPokemon();
        addCatchPokemonRoundService.addRound(catchPokemon);
        return new CatchPokemonSkipResponse(pokemon);
    }
}
