package wlsh.project.discordgames.catchgames.catchmusic.application.hint.context;

import wlsh.project.discordgames.catchgames.catchmusic.application.hint.Hint;
import wlsh.project.discordgames.catchgames.catchmusic.application.hint.TitleHintState;
import wlsh.project.discordgames.catchgames.catchmusic.application.hint.dto.TitleHintResult;

public class TitleHintContext {

    private TitleHintState state;
    private Hint hint;

    public TitleHintContext(String answer) {
        this.hint = new Hint(answer);
        this.state = new DefaultTitleHintState();
    }

    public TitleHintResult getHint() {
        return state.getHint(this, hint);
    }

    public void setState(TitleHintState state) {
        this.state = state;
    }

    public boolean isCurrentHintContext(String answer) {
        return hint.getAnswer().equals(answer);
    }
}
