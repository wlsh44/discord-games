package wlsh.project.discordgames.catchmusic.application;

import wlsh.project.discordgames.catchmusic.application.hint.Hint;
import wlsh.project.discordgames.catchmusic.application.hint.context.AnswerInfo;

public record TitleHintResult(
        String title,
        int length,
        boolean hangul,
        boolean english,
        boolean specialCharacter
) {
    public static TitleHintResult of(Hint hint) {
        return new TitleHintResult(
                hint.getHint(),
                hint.getLength(),
                hint.isHangul(),
                hint.isEnglish(),
                hint.isSpecialCharacter()
        );
    }
}
