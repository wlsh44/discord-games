package wlsh.project.discordgames.catchmusic.application.hint.context;

import org.junit.jupiter.api.Test;

class TitleHintContextTest {

    @Test
    void hintTest() throws Exception {
        //given
        TitleHintContext context = new TitleHintContext("C'mon Through");

        //when
        System.out.println(context.getHint().title());
        System.out.println(context.getHint().title());
        System.out.println(context.getHint().title());
        System.out.println(context.getHint().title());

        //then

    }

    @Test
    void hintTest2() throws Exception {
        //given
        TitleHintContext context = new TitleHintContext("잘 지내자, 우리");

        //when
        System.out.println(context.getHint().title());
        System.out.println(context.getHint().title());
        System.out.println(context.getHint().title());
        System.out.println(context.getHint().title());
        System.out.println(context.getHint().title());

        //then

    }

    @Test
    void hintTest3() throws Exception {
        //given
        TitleHintContext context = new TitleHintContext("test테스트ㅌ");

        //when
        System.out.println(context.getHint().title());
        System.out.println(context.getHint().title());
        System.out.println(context.getHint().title());
        System.out.println(context.getHint().title());
        System.out.println(context.getHint().title());

        //then

    }

    @Test
    void hintTest4() throws Exception {
        //given
        TitleHintContext context = new TitleHintContext("ㅌㅅㅌ");

        //when
        System.out.println(context.getHint().title());
        System.out.println(context.getHint().title());
        System.out.println(context.getHint().title());
        System.out.println(context.getHint().title());
        System.out.println(context.getHint().title());

        //then

    }
}
