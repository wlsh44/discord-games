package wlsh.project.discordgames.catchposkill.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchpokemon.application.dto.CatchPokemonAnswerResult;
import wlsh.project.discordgames.catchpokemon.domain.CatchPokemon;
import wlsh.project.discordgames.catchpokemon.domain.Pokemon;
import wlsh.project.discordgames.catchposkill.application.dto.CatchPoSkillAnswerResult;
import wlsh.project.discordgames.catchposkill.domain.CatchPoSkill;
import wlsh.project.discordgames.catchposkill.domain.PoSkill;
import wlsh.project.discordgames.catchposkill.infra.CatchPoSkillRepository;
import wlsh.project.discordgames.common.catchgames.application.CatchGameStatusService;
import wlsh.project.discordgames.common.catchgames.application.dto.CatchGameStatus;

@Service
@RequiredArgsConstructor
public class CatchPoSkillAnswerCorrectService {

    private final CatchPoSkillRepository catchPoSkillRepository;
    private final AddCatchPoSkillRoundService addCatchPoSkillRoundService;
    private final CatchGameStatusService catchGameStatusService;

    public CatchPoSkillAnswerResult handleAnswerCorrect(CatchPoSkill catchPoSkill) {
        CatchGameStatus status = catchGameStatusService.getStatus(catchPoSkill);

        if (catchPoSkill.isFinished()) {
            catchPoSkillRepository.delete(catchPoSkill.getCatchGameId());
            return CatchPoSkillAnswerResult.finish(status);
        } else {
            PoSkill poSkill = catchPoSkill.getCurrentRound().getPoSkill();
            addCatchPoSkillRoundService.addRound(catchPoSkill);
            return CatchPoSkillAnswerResult.correct(poSkill, status);
        }
    }

}
