package wlsh.project.discordgames.catchmusic.application.hint.context;

import java.util.regex.Pattern;

public record AnswerInfo(
        String answer,
        String currentHint,
        boolean hangul,
        boolean english,
        boolean number,
        boolean specialCharacter,
        int length
) {
    private static final Pattern specialCharacterPattern = Pattern.compile("[^A-Za-z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s]");
    private static final Pattern hangulPattern = Pattern.compile("[가-힣ㄱ-ㅎㅏ-ㅣ]");
    private static final Pattern englishPattern = Pattern.compile("[A-Za-z]");
    private static final Pattern numberPattern = Pattern.compile("[0-9]");

    public static AnswerInfo of(String answer) {
        boolean hangul = isCharacterContain(hangulPattern, answer);
        boolean english = isCharacterContain(englishPattern, answer);
        boolean number = isCharacterContain(numberPattern, answer);
        boolean specialCharacters = isCharacterContain(specialCharacterPattern, answer);
        int length = answer.replaceAll(" ", "").length();
        return new AnswerInfo(answer, null, hangul, english, number, specialCharacters, length);
    }

    private static boolean isCharacterContain(Pattern pattern, String answer) {
        return pattern.matcher(answer).find();
    }

    public AnswerInfo changeHint(String hint) {
        return new AnswerInfo(answer, hint, hangul, english, number, specialCharacter, length);
    }
}
