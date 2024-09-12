package wlsh.project.discordgames.catchmusic.application.hint;

import wlsh.project.discordgames.catchmusic.application.hint.context.TitleHintContext;
import wlsh.project.discordgames.catchmusic.application.hint.dto.TitleHintResult;

public interface TitleHintState {

    TitleHintResult getHint(TitleHintContext context, Hint hint);

}
