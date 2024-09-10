package wlsh.project.discordgames.pokemon.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.catchmusic.application.hint.dto.TitleHintResult;
import wlsh.project.discordgames.common.catchgames.application.AnswerHintService;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.pokemon.domain.CatchPokemon;
import wlsh.project.discordgames.pokemon.infra.CatchPokemonRepository;
import wlsh.project.discordgames.pokemon.domain.CatchPokemonRound;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CatchPokemonNameHintUseCase {

    private final CatchPokemonRepository catchPokemonRepository;
    private final AnswerHintService answerHintService;

    public TitleHintResult getAnswerHint(CatchGameId catchGameId) {
        CatchPokemon catchPokemon = catchPokemonRepository.findById(catchGameId)
                .orElseThrow(() -> new RuntimeException("없음"));
        CatchPokemonRound round = catchPokemon.getCurrentRound();
        return answerHintService.getAnswerHint(catchGameId, round);
    }
}
