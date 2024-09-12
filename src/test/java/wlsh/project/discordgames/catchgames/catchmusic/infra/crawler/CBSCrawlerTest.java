package wlsh.project.discordgames.catchgames.catchmusic.infra.crawler;

import org.junit.jupiter.api.Test;
import wlsh.project.discordgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchmusic.infra.crawler.CBSCrawler;
import wlsh.project.discordgames.catchmusic.infra.crawler.CBSRadio;

import java.util.List;

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
