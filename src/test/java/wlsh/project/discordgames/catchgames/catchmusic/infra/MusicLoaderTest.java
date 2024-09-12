package wlsh.project.discordgames.catchgames.catchmusic.infra;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wlsh.project.discordgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchmusic.infra.MusicLoader;
import wlsh.project.discordgames.catchmusic.infra.crawler.CBSRadio;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MusicLoaderTest {

    @Autowired
    MusicLoader musicLoader;

    @Test
    void loadMusic() throws Exception {
        //given

        List<Music> musicList = new ArrayList<>();
        while (musicList.size() < 10) {
            Music music = musicLoader.loadMusic(new CatchMusic(new CatchGameId("1", "1"), 1, List.of(), CBSRadio.MORNING, 10));
            try {
                LocalDate date = LocalDate.parse(music.releaseDate(), DateTimeFormatter.ISO_DATE);
                if (date.isBefore(LocalDate.of(2000, 1, 1))) {
                    continue;
                }
            } catch (Exception e) {
                continue;
            }
            musicList.add(music);
        }
        //when
        for (Music music : musicList) {
            System.out.println("title: %s, artist: %s, popularity: %d, releaseDate: %s".formatted(music.name(), music.artist().name(), music.popularity(), music.releaseDate()));
        }

        //then

    }
}
