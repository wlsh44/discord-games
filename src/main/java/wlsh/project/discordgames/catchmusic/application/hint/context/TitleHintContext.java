package wlsh.project.discordgames.catchmusic.application.hint.context;

import wlsh.project.discordgames.catchmusic.application.TitleHintResult;
import wlsh.project.discordgames.catchmusic.application.hint.Hint;
import wlsh.project.discordgames.catchmusic.application.hint.TitleHintState;

import java.util.HashMap;
import java.util.Map;

public class TitleHintContext {

    private final Map<String, TitleHintState> contextMap = new HashMap<>();
//    private String answer;
    private TitleHintState state;
    //    private AnswerInfo answerInfo;
    private Hint hint;

    public TitleHintContext(String answer) {
//        this.answerInfo = AnswerInfo.of(answer);
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
