package wlsh.project.discordgames.catchmusic.application.hint.context;

import wlsh.project.discordgames.catchmusic.application.hint.Hint;
import wlsh.project.discordgames.catchmusic.application.hint.HintCharacter;
import wlsh.project.discordgames.catchmusic.application.hint.TitleHintState;
import wlsh.project.discordgames.catchmusic.application.hint.dto.TitleHintResult;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PartialRevealHintState implements TitleHintState {
    @Override
    public TitleHintResult getHint(TitleHintContext context, Hint hint) {
        int hintLength = hint.getLength();
        int revealCount = hintLength / 3;
        if (revealCount == 0) {
            revealCount = 1;
        }

        Random random = new Random();
        Set<Integer> revealedIndexSet = new HashSet<>();

        List<HintCharacter> hintCharacters = hint.getHintCharacters();
        while (revealedIndexSet.size() != revealCount) {
            int num = random.nextInt(hintCharacters.size());
            HintCharacter hintCharacter = hintCharacters.get(num);
            if (hintCharacter.isWhiteSpace()) {
                continue;
            }
            hintCharacter.reveal();
            revealedIndexSet.add(num);
        }
        return TitleHintResult.of(hint);
    }
}
