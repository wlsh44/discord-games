package wlsh.project.discordgames.catchmusic.application.hint;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchmusic.application.hint.context.TitleHintContext;
import wlsh.project.discordgames.catchmusic.application.hint.dto.TitleHintResult;
import wlsh.project.discordgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchmusic.domain.CatchMusicRound;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MusicNameHintService {

    private final CatchMusicRepository catchMusicRepository;
    private final Map<CatchGameId, TitleHintContext> contextMap = new HashMap<>();

    public TitleHintResult getMusicNameHint(CatchGameId catchGameId) {
        CatchMusic catchMusic = catchMusicRepository.findByCatchGameId(catchGameId)
                .orElseThrow(() -> new RuntimeException("없음"));
        CatchMusicRound round = catchMusic.getCurrentRound();
        String answer = round.getAnswer();
        TitleHintContext context = contextMap.getOrDefault(catchGameId, new TitleHintContext(answer));
        if (!context.isCurrentHintContext(answer)) {
            context = new TitleHintContext(answer);
        }
        contextMap.put(catchGameId, context);
        return context.getHint();
    }
}
