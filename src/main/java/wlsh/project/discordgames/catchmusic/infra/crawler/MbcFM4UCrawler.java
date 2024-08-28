package wlsh.project.discordgames.catchmusic.infra.crawler;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchmusic.domain.Music;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static wlsh.project.discordgames.catchmusic.infra.crawler.CrawlerConfig.MBC_FM4U_URL;

@Slf4j
@Component
public class MbcFM4UCrawler {

    public List<Music> crawl(Radio radio) {
        try {
            int seqID = new Random().nextInt(radio.getMinSeqId(), radio.getMaxSeqId() + 1);
            String url = MBC_FM4U_URL + "?seqID=" + seqID + "&progCode=" + radio.getProgCode();

            log.info("search artistUrl: {}", url);

            Document doc = Jsoup.connect(url).get();

            Elements rows = doc.select("tbody tr");
            ArrayList<Music> musicList = new ArrayList<>();
            for (Element row : rows) {
                if (row.select("td").size() > 1) {
                    String title = row.select("td").get(1).text().replaceAll("`", "'");
                    String artist = row.select("td").get(2).text();

                    if (Radio.MUSIC_PARTY.equals(radio) && ("Over The Sea".equals(title) || "So What's New?".equals(title) || "봄바람".equals(title) || "Kids".equals(title))) {
                        continue;
                    } else if (Radio.BRUNCH_CAFE.equals(radio) && ("구름 위를 걷다".equals(title))) {
                        continue;
                    } else if (Radio.STARNIGHT.equals(radio) && title.contains("가을의 기도")) {
                        continue;
                    }

                    log.info("노래: {}, 가수: {}", title, artist);
                    musicList.add(Music.of(title, artist));
                }
            }
            return musicList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
