package wlsh.project.discordgames.catchmusic.application.hint.context;

import wlsh.project.discordgames.catchmusic.application.TitleHintResult;
import wlsh.project.discordgames.catchmusic.application.hint.CharacterCategory;
import wlsh.project.discordgames.catchmusic.application.hint.Hint;
import wlsh.project.discordgames.catchmusic.application.hint.TitleHintState;

import java.util.regex.Pattern;

public class DefaultTitleHintState implements TitleHintState {

    @Override
    public TitleHintResult getHint(TitleHintContext context, Hint hint) {
        hint.getHintCharacters().stream()
                .filter(hintCharacter -> !hintCharacter.isWhiteSpace())
                .forEach(hintCharacter -> hintCharacter.updateHint("⚪️"));

        if (hint.isSpecialCharacter()) {
            context.setState(new SpecialCharacterHintState());
        } else if (hint.isHangul()) {
            context.setState(new KoreanInitialHintState());
        } else {
            context.setState(new FirstLetterHintState());
        }

        return TitleHintResult.of(hint);
    }
}
