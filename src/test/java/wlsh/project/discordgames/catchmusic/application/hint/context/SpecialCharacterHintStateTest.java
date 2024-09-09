package wlsh.project.discordgames.catchmusic.application.hint.context;

import org.junit.jupiter.api.Test;
import wlsh.project.discordgames.catchgames.catchmusic.application.hint.Hint;
import wlsh.project.discordgames.catchgames.catchmusic.application.hint.context.SpecialCharacterHintState;
import wlsh.project.discordgames.catchgames.catchmusic.application.hint.context.TitleHintContext;
import wlsh.project.discordgames.catchgames.catchmusic.application.hint.dto.TitleHintResult;

import static org.mockito.Mockito.mock;

class SpecialCharacterHintStateTest {

    @Test
    void test() throws Exception {
        //given
        SpecialCharacterHintState state = new SpecialCharacterHintState();

        //when
        TitleHintResult hint = state.getHint(mock(TitleHintContext.class), new Hint("C'mon Through"));

        //then
        System.out.println("hint.title() = " + hint.title());
    }

}
