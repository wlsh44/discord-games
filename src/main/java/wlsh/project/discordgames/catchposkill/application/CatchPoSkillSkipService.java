package wlsh.project.discordgames.catchposkill.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.catchpokemon.application.AddCatchPokemonRoundService;
import wlsh.project.discordgames.catchpokemon.application.dto.CatchPokemonSkipResponse;
import wlsh.project.discordgames.catchpokemon.domain.CatchPokemon;
import wlsh.project.discordgames.catchpokemon.domain.Pokemon;
import wlsh.project.discordgames.catchpokemon.infra.CatchPokemonRepository;
import wlsh.project.discordgames.catchposkill.application.dto.CatchPoSkillSkipResponse;
import wlsh.project.discordgames.catchposkill.domain.CatchPoSkill;
import wlsh.project.discordgames.catchposkill.domain.PoSkill;
import wlsh.project.discordgames.catchposkill.infra.CatchPoSkillRepository;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;

@Service
@Transactional
@RequiredArgsConstructor
public class CatchPoSkillSkipService {

    private final CatchPoSkillRepository catchPokemonRepository;
    private final AddCatchPoSkillRoundService addCatchPokemonRoundService;

    public CatchPoSkillSkipResponse skip(CatchGameId catchGameId) {
        CatchPoSkill catchPoSkill = catchPokemonRepository.findById(catchGameId)
                .orElseThrow(() -> new RuntimeException("없음"));
        PoSkill poSkill = catchPoSkill.getCurrentRound().getPoSkill();
        addCatchPokemonRoundService.addRound(catchPoSkill);
        return new CatchPoSkillSkipResponse(poSkill);
    }
}
