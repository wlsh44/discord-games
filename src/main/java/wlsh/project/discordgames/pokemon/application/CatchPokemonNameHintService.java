package wlsh.project.discordgames.pokemon.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.catchmusic.application.TitleHintResult;
import wlsh.project.discordgames.catchmusic.application.hint.context.TitleHintContext;
import wlsh.project.discordgames.pokemon.domain.CatchPokemon;
import wlsh.project.discordgames.pokemon.domain.CatchPokemonRepository;
import wlsh.project.discordgames.pokemon.domain.Round2;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CatchPokemonNameHintService {

    //    private final CatchMusicRepository catchMusicRepository;
    private final CatchPokemonRepository catchPokemonRepository;
    private final Map<String, TitleHintContext> contextMap = new HashMap<>();

    public TitleHintResult getNameHint(String guildId) {
        CatchPokemon catchPokemon = catchPokemonRepository.get(guildId)
                .orElseThrow(() -> new RuntimeException("없음"));
        Round2 round = catchPokemon.getCurrentRound();
        String answer = round.getKoAnswer();
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
