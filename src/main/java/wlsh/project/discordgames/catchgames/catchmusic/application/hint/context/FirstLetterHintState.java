package wlsh.project.discordgames.catchgames.catchmusic.application.hint.context;

import wlsh.project.discordgames.catchgames.catchmusic.application.hint.Hint;
import wlsh.project.discordgames.catchgames.catchmusic.application.hint.TitleHintState;
import wlsh.project.discordgames.catchgames.catchmusic.application.hint.dto.TitleHintResult;

public class FirstLetterHintState implements TitleHintState {
    @Override
    public TitleHintResult getHint(TitleHintContext context, Hint hint) {
        hint.getHintCharacters().get(0).reveal();
//        StringBuilder hint = new StringBuilder();
//        boolean firstLetterRevealed = false;
//        int length = answerInfo.replaceAll(" ", "").length();
//        for (char ch : answerInfo.toCharArray()) {
//            if (!firstLetterRevealed && !Character.isWhitespace(ch)) {
//                hint.append(ch);
//                firstLetterRevealed = true;
//            } else if (Character.isWhitespace(ch)) {
//                hint.append("  ");
//            } else {
//                hint.append("⚪️");
//            }
//        }

        context.setState(new PartialRevealHintState());

        return TitleHintResult.of(hint);
    }
}
