package wlsh.project.discordgames.catchgames.catchmusic.application.hint;

import wlsh.project.discordgames.catchgames.catchmusic.application.hint.context.TitleHintContext;
import wlsh.project.discordgames.catchgames.catchmusic.application.hint.dto.TitleHintResult;

public interface TitleHintState {

    TitleHintResult getHint(TitleHintContext context, Hint hint);

}
