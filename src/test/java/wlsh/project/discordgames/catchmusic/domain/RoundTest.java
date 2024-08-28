package wlsh.project.discordgames.catchmusic.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {


    @Test
    @DisplayName("나비와고양이")
    void test() throws Exception {
        //given


        //when
        Round round = new Round(1, Music.of("나비와 고양이 (feat.백현 (BAEKHYUN))", "볼빨간 사춘기"));
        Round round2 = new Round(1, Music.of("나비와 고양이 (feat.백현 )", "볼빨간 사춘기"));
        Round round3 = new Round(1, Music.of("나비와 고양이 (feat.백현 )", "볼빨간 사춘기"));
        Round round4 = new Round(1, Music.of("나비와 고양이 ", "볼빨간 사춘기"));

        //then
        String answer = round.getAnswer();
        System.out.println("answer = [" + answer + "]");
        System.out.println("answer = [" + round3.getAnswer() + "]");
        System.out.println("answer = [" + round2.getAnswer() + "]");
        System.out.println("answer = [" + round4.getAnswer() + "]");
    }
}
