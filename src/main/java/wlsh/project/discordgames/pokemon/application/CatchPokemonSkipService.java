package wlsh.project.discordgames.pokemon.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.catchmusic.application.dto.SkipResponse;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.pokemon.application.dto.CatchPokemonSkipResponse;
import wlsh.project.discordgames.pokemon.domain.CatchPokemon;
import wlsh.project.discordgames.pokemon.domain.Pokemon;
import wlsh.project.discordgames.pokemon.infra.CatchPokemonRepository;

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
