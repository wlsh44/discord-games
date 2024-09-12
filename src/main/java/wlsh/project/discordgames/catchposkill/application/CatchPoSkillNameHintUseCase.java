package wlsh.project.discordgames.catchposkill.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.catchmusic.application.hint.dto.TitleHintResult;
import wlsh.project.discordgames.catchposkill.domain.CatchPoSkill;
import wlsh.project.discordgames.catchposkill.domain.CatchPoSkillRound;
import wlsh.project.discordgames.catchposkill.infra.CatchPoSkillRepository;
import wlsh.project.discordgames.common.catchgames.application.AnswerHintService;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CatchPoSkillNameHintUseCase {

    private final CatchPoSkillRepository catchPoSkillRepository;
    private final AnswerHintService answerHintService;

    public TitleHintResult getAnswerHint(CatchGameId catchGameId) {
        CatchPoSkill catchPoSkill = catchPoSkillRepository.findById(catchGameId)
                .orElseThrow(() -> new RuntimeException("없음"));
        CatchPoSkillRound round = catchPoSkill.getCurrentRound();
        return answerHintService.getAnswerHint(catchGameId, round);
    }
}
