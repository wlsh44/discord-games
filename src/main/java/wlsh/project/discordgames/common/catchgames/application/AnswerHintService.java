package wlsh.project.discordgames.common.catchgames.application;

import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchmusic.application.hint.context.TitleHintContext;
import wlsh.project.discordgames.catchmusic.application.hint.dto.TitleHintResult;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.common.catchgames.domain.Round;
import wlsh.project.discordgames.pokemon.domain.CatchPokemonRound;

import java.util.HashMap;
import java.util.Map;

@Service
public class AnswerHintService {

    private final Map<CatchGameId, TitleHintContext> contextMap = new HashMap<>();

    public TitleHintResult getAnswerHint(CatchGameId catchGameId, Round round) {
        String answer = round.getAnswer().main();
        TitleHintContext context = contextMap.getOrDefault(catchGameId, new TitleHintContext(answer));
        if (!context.isCurrentHintContext(answer)) {
            context = new TitleHintContext(answer);
        }
        contextMap.put(catchGameId, context);
        return context.getHint();
    }
}
