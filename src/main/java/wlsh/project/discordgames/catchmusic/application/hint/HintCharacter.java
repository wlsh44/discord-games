package wlsh.project.discordgames.catchmusic.application.hint;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static wlsh.project.discordgames.catchmusic.application.hint.CharacterCategory.WHITE_SPACE;


@Getter
@AllArgsConstructor
public class HintCharacter {

    private String original;
    private String hint;
    private CharacterCategory category;
    private boolean revealed;

    public static HintCharacter newHint(String original, CharacterCategory category) {
        String hint = null;
        if (WHITE_SPACE.equals(category)) {
            hint = original;
        }
        return new HintCharacter(original, hint, category, false);
    }

    public char getOriginalChar() {
        return original.charAt(0);
    }

    public void updateHint(String hint) {
        this.hint = hint;
    }

    public boolean isWhiteSpace() {
        return WHITE_SPACE.equals(category);
    }

    public void reveal() {
        this.hint = original;
    }
}
