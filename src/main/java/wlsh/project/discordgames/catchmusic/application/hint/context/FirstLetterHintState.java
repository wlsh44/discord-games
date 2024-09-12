package wlsh.project.discordgames.catchmusic.application.hint.context;

import wlsh.project.discordgames.catchmusic.application.hint.Hint;
import wlsh.project.discordgames.catchmusic.application.hint.TitleHintState;
import wlsh.project.discordgames.catchmusic.application.hint.dto.TitleHintResult;

public class FirstLetterHintState implements TitleHintState {
    @Override
    public TitleHintResult getHint(TitleHintContext context, Hint hint) {
        hint.getHintCharacters().get(0).reveal();

        context.setState(new PartialRevealHintState());

        return TitleHintResult.of(hint);
    }
}
