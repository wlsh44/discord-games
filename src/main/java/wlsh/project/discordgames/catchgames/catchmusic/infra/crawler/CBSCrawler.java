package wlsh.project.discordgames.catchgames.catchmusic.infra.crawler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class CBSCrawler implements RadioCrawler {

    private static final String CBS_URL = "https://www.cbs.co.kr/program/playlist/%s?date=%s";

    public List<Music> crawl(Radio radio) {
        try {
            String url = CBS_URL.formatted(radio.getProgramCode(), radio.getRandom());

            log.info("search artistUrl: {}", url);

            Document doc = Jsoup.connect(url).get();

            Elements rows = doc.select("div.play-list")
                    .select("ul")
                    .select("li.article");
            ArrayList<Music> musicList = new ArrayList<>();
            for (Element row : rows) {
                String title = row.select("div.title").text().replaceAll("`", "'");
                String name = row.select("div.name").text();

                Music music = Music.of(title, name);
                musicList.add(music);
            }
            return musicList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
