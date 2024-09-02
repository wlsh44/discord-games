package wlsh.project.discordgames.catchgames.catchmusic.application.hint;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchgames.catchmusic.application.hint.context.TitleHintContext;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusicRound;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchgames.common.domain.CatchGameId;
import wlsh.project.discordgames.catchmusic.application.TitleHintResult;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MusicNameHintService {

    private final CatchMusicRepository catchMusicRepository;
    private final Map<CatchGameId, TitleHintContext> contextMap = new HashMap<>();

    public TitleHintResult getMusicNameHint(CatchGameId catchGameId) {
        CatchMusic catchMusic = catchMusicRepository.findByCatchGameId(catchGameId)
                .orElseThrow(() -> new RuntimeException("없음"));
        Music music = ((CatchMusicRound)catchMusic.getCurrentRound()).getMusic();
        String answer = music.name();
        TitleHintContext context = contextMap.getOrDefault(catchGameId, new TitleHintContext(answer));
        if (!context.isCurrentHintContext(answer)) {
            context = new TitleHintContext(answer);
        }
        contextMap.put(catchGameId, context);
        return context.getHint();
    }

    private boolean isCharacterContain(String regex, String answer) {
        Pattern containSpecialCharacters = Pattern.compile(regex);
        return containSpecialCharacters.matcher(answer).find();
    }
}
