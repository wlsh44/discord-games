package wlsh.project.discordgames.catchmusic.application.hint.context;

import org.junit.jupiter.api.Test;
import wlsh.project.discordgames.catchmusic.application.TitleHintResult;
import wlsh.project.discordgames.catchmusic.application.hint.Hint;

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
