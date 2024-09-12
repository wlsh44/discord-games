package wlsh.project.discordgames.catchposkill.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.catchposkill.application.dto.CatchPoSkillAnswerResult;
import wlsh.project.discordgames.catchposkill.domain.CatchPoSkill;
import wlsh.project.discordgames.catchposkill.domain.CatchPoSkillRound;
import wlsh.project.discordgames.catchposkill.infra.CatchPoSkillRepository;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.common.catchgames.domain.Player;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CatchPoSkillAnswerUseCase {

    private final CatchPoSkillRepository catchPoSkillRepository;
    private final CatchPoSkillAnswerCorrectService catchPoSkillAnswerCorrectService;

    public CatchPoSkillAnswerResult answer(CatchGameId catchGameId, Player player) {
        Optional<CatchPoSkill> catchPokemonOptional = catchPoSkillRepository.findById(catchGameId);
        if (catchPokemonOptional.isEmpty()) {
            return CatchPoSkillAnswerResult.INCORRECT;
        }
        CatchPoSkill catchPoSkill = catchPokemonOptional.get();
        CatchPoSkillRound round = catchPoSkill.getCurrentRound();

        if (!round.answer(player)) {
            return CatchPoSkillAnswerResult.INCORRECT;
        }
        return catchPoSkillAnswerCorrectService.handleAnswerCorrect(catchPoSkill);
    }
}
