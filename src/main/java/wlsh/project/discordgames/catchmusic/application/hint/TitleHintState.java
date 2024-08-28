package wlsh.project.discordgames.catchmusic.application.hint;

import wlsh.project.discordgames.catchmusic.application.TitleHintResult;
import wlsh.project.discordgames.catchmusic.application.hint.context.AnswerInfo;
import wlsh.project.discordgames.catchmusic.application.hint.context.TitleHintContext;

public interface TitleHintState {

    TitleHintResult getHint(TitleHintContext context, Hint hint);

}
