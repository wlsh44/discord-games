package wlsh.project.discordgames.catchmusic.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class MusicNameHintServiceTest {

    @Test
    @DisplayName("")
    void asd() throws Exception {
        //given
        String answer = "나비와 고양이";
        Pattern containKorean = Pattern.compile("[가-힣ㄱ-ㅎㅏ-ㅣ]");
        boolean korean = containKorean.matcher(answer).find();
        Pattern containEnglish = Pattern.compile("[A-Za-z]");
        boolean english = containEnglish.matcher(answer).find();
        Pattern containSpecialCharacters = Pattern.compile("[^A-Za-z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s]");
        boolean specialCharacters = containSpecialCharacters.matcher(answer).find();
        String lengthHint = answer.replaceAll("\\S", "⚪️");
        String titleHint = lengthHint + "(한글: %s 영어: %s 특수 문자: %s)".formatted(
                korean ? "✅" : "❌",
                english ? "✅" : "❌",
                specialCharacters ? "✅" : "❌"
        );
        System.out.println("titleHint = " + titleHint);
        //when


        //then

    }
}
