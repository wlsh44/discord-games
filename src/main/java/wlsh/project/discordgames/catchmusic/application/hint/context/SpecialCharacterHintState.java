package wlsh.project.discordgames.catchmusic.application.hint.context;

import wlsh.project.discordgames.catchmusic.application.TitleHintResult;
import wlsh.project.discordgames.catchmusic.application.hint.Hint;
import wlsh.project.discordgames.catchmusic.application.hint.HintCharacter;
import wlsh.project.discordgames.catchmusic.application.hint.TitleHintState;

import java.util.regex.Pattern;

import static wlsh.project.discordgames.catchmusic.application.hint.CharacterCategory.SPECIAL;

public class SpecialCharacterHintState implements TitleHintState {


    @Override
    public TitleHintResult getHint(TitleHintContext context, Hint hint) {
        hint.getHintCharacters().stream()
                .filter(hintCharacter -> SPECIAL.equals(hintCharacter.getCategory()))
                .forEach(HintCharacter::reveal);

        if (hint.isHangul()) {
            context.setState(new KoreanInitialHintState());
        } else {
            context.setState(new FirstLetterHintState());
        }

        return TitleHintResult.of(hint);
    }
}
