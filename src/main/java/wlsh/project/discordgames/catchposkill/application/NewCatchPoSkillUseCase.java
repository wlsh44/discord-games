package wlsh.project.discordgames.catchposkill.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchpokemon.domain.CatchPokemon;
import wlsh.project.discordgames.catchpokemon.domain.GenerationFilter;
import wlsh.project.discordgames.catchpokemon.domain.PokemonFilterOption;
import wlsh.project.discordgames.catchposkill.domain.CatchPoSkill;
import wlsh.project.discordgames.catchposkill.infra.CatchPoSkillRepository;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewCatchPoSkillUseCase {

    private final CatchPoSkillRepository catchPoSkillRepository;
    private final AddCatchPoSkillRoundService addCatchPoSkillRoundService;

    public void newCatchPoSkill(CatchGameId catchGameId, int finishScore) {
        CatchPoSkill catchPoSkill = catchPoSkillRepository.save(CatchPoSkill.startGame(catchGameId, finishScore));
        addCatchPoSkillRoundService.addRound(catchPoSkill);
    }
}
