package wlsh.project.discordgames.catchmusic.application.hint;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.catchmusic.application.TitleHintResult;
import wlsh.project.discordgames.catchmusic.application.hint.context.TitleHintContext;
import wlsh.project.discordgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchmusic.domain.Round;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MusicNameHintService {

    private final CatchMusicRepository catchMusicRepository;
    private final Map<String, TitleHintContext> contextMap = new HashMap<>();

    public TitleHintResult getMusicNameHint(String guildId) {
        CatchMusic catchMusic = catchMusicRepository.findByGuildId(guildId)
                .orElseThrow(() -> new RuntimeException("없음"));
        Round round = catchMusic.getCurrentRound();
        String answer = round.getAnswer();
        TitleHintContext context = contextMap.getOrDefault(guildId, new TitleHintContext(answer));
        if (!context.isCurrentHintContext(answer)) {
            context = new TitleHintContext(answer);
        }
        contextMap.put(guildId, context);
        return context.getHint();
    }

    private boolean isCharacterContain(String regex, String answer) {
        Pattern containSpecialCharacters = Pattern.compile(regex);
        return containSpecialCharacters.matcher(answer).find();
    }
}
