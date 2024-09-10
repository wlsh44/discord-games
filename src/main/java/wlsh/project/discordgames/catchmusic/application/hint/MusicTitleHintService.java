package wlsh.project.discordgames.catchmusic.application.hint;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchmusic.application.hint.context.TitleHintContext;
import wlsh.project.discordgames.catchmusic.application.hint.dto.TitleHintResult;
import wlsh.project.discordgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchmusic.domain.CatchMusicRound;
import wlsh.project.discordgames.common.catchgames.application.AnswerHintService;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MusicTitleHintService {

    private final CatchMusicRepository catchMusicRepository;
    private final AnswerHintService answerHintService;

    public TitleHintResult getMusicNameHint(CatchGameId catchGameId) {
        CatchMusic catchMusic = catchMusicRepository.findByCatchGameId(catchGameId)
                .orElseThrow(() -> new RuntimeException("없음"));
        CatchMusicRound round = catchMusic.getCurrentRound();
        return answerHintService.getAnswerHint(catchGameId, round);
    }
}
