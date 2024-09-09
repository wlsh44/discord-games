package wlsh.project.discordgames.catchmusic.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.*;

class MusicTest {


    @Test
    @DisplayName("secondName 테스트")
    void secondNameTest() throws Exception {
        //given


        //when

        assertSoftly(s -> {
            s.assertThat(Music.of("Umbrella (Radio Edit) (Feat. JAY-Z)", "Rihanna").secondName()).isEqualTo(null);
            s.assertThat(Music.of("My Song (Acoustic Version)", "Rihanna").secondName()).isEqualTo(null);
            s.assertThat(Music.of("%%(응응)", "Rihanna").secondName()).isEqualTo("응응");
            s.assertThat(Music.of("Another Song (feat. Artist)", "Rihanna").secondName()).isEqualTo(null);
            s.assertThat(Music.of("Duet Song (Duet Version)", "Rihanna").secondName()).isEqualTo(null);
            s.assertThat(Music.of("Unknown Song", "Rihanna").secondName()).isEqualTo(null);
            s.assertThat(Music.of("길 (Song by 아이유, 헨리, 조현아, 양다일)", "Rihanna").secondName()).isEqualTo(null);
            s.assertThat(Music.of("신청곡 (Feat. SUGA of BTS)", "Rihanna").secondName()).isEqualTo(null);
            s.assertThat(Music.of("해야 (Sunrise)", "Rihanna").secondName()).isEqualTo("Sunrise");
            s.assertThat(Music.of("All Night (전화해)", "Rihanna").secondName()).isEqualTo("전화해");
        });


        //then

    }
}
