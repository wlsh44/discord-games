package wlsh.project.discordgames.catchgames.catchmusic.application.hint;

import lombok.Getter;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter
public class Hint {

    private static final Pattern specialCharacterPattern = Pattern.compile("[^A-Za-z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s]");
    private static final Pattern hangulPattern = Pattern.compile("[가-힣ㄱ-ㅎㅏ-ㅣ]");
    private static final Pattern englishPattern = Pattern.compile("[A-Za-z]");
    private static final Pattern numberPattern = Pattern.compile("[0-9]");

    private String answer;
    private List<HintCharacter> hint;
    private boolean hangul;
    private boolean english;
    private boolean number;
    private boolean specialCharacter;

    public Hint(String answer) {
        this.answer = answer;
        this.hint = answer.chars().mapToObj(ch -> {
            String c = String.valueOf((char) ch);
            if (isCharacterContain(hangulPattern, c)) {
                this.hangul = true;
                return HintCharacter.newHint(c, CharacterCategory.HANGUL);
            } else if (isCharacterContain(englishPattern, c)) {
                this.english = true;
                return HintCharacter.newHint(c, CharacterCategory.ENGLISH);
            } else if (isCharacterContain(numberPattern, c)) {
                this.number = true;
                return HintCharacter.newHint(c, CharacterCategory.NUMBER);
            } else if (Character.isWhitespace(ch)) {
                return HintCharacter.newHint(c, CharacterCategory.WHITE_SPACE);
            } else {
                this.specialCharacter = true;
                return HintCharacter.newHint(c, CharacterCategory.SPECIAL);
            }
        }).toList();
    }

    private boolean isCharacterContain(Pattern pattern, String answer) {
        return pattern.matcher(answer).find();
    }

    public List<HintCharacter> getHintCharacters() {
        return hint;
    }

    public String getHint() {
        return hint.stream()
                .map(HintCharacter::getHint)
                .map(String::valueOf)
                .collect(Collectors.joining(""))
                .replaceAll(" ", "  ");
    }

    public int getLength() {
        return (int) hint.stream()
                .filter(hintCharacter -> !hintCharacter.isWhiteSpace())
                .count();
    }
}
