package wlsh.project.discordgames.catchgames.catchmusic.application.hint.dto;

import wlsh.project.discordgames.catchgames.catchmusic.application.hint.Hint;

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
