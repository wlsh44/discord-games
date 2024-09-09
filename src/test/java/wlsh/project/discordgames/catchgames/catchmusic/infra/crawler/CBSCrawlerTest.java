package wlsh.project.discordgames.catchgames.catchmusic.infra.crawler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CBSCrawlerTest {

    @Test
    void cbs() throws Exception {
        //given
        CBSCrawler cbsCrawler = new CBSCrawler();

        //when
        List<Music> musicList = cbsCrawler.crawl(CBSRadio.MORNING);

        //then
        System.out.println("musicList = " + musicList);
    }
}
