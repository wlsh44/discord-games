package wlsh.project.discordgames.catchgames.catchmusic.application.hint.context;

import wlsh.project.discordgames.catchgames.catchmusic.application.hint.Hint;
import wlsh.project.discordgames.catchgames.catchmusic.application.hint.TitleHintState;
import wlsh.project.discordgames.catchgames.catchmusic.application.hint.dto.TitleHintResult;

import static wlsh.project.discordgames.catchgames.catchmusic.application.hint.CharacterCategory.HANGUL;


public class KoreanInitialHintState implements TitleHintState {

    private static final int ㄱ = 0x3131;
    private static final int ㅎ = 0x314E;
    private static final int 가 = 0xAC00;
    private static final int 힣 = 0xD7A3;
    private static final int JUNGSUNG = 21;
    private static final int JONGSUNG = 28;
    private final char[] CHO_SUNG = {'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};

    @Override
    public TitleHintResult getHint(TitleHintContext context, Hint hint) {
        hint.getHintCharacters().stream()
                .filter(hintCharacter -> HANGUL.equals(hintCharacter.getCategory()))
                .forEach(hintCharacter -> {
                    int originalChar = hintCharacter.getOriginalChar();

                    if (originalChar >= ㄱ && originalChar <= ㅎ) {
                        hintCharacter.updateHint(String.valueOf((char) originalChar));
                    } else if (originalChar >= 가 && originalChar <= 힣) {
                        int unicode = originalChar - 가;
                        int choSungIndex = unicode / (JUNGSUNG * JONGSUNG);

                        hintCharacter.updateHint(String.valueOf(getChoSung(choSungIndex)));
                    }
                });

        context.setState(new FirstLetterHintState());

        return TitleHintResult.of(hint);
    }

    private char getChoSung(int choSungIndex) {
        return CHO_SUNG[choSungIndex];
    }

}
