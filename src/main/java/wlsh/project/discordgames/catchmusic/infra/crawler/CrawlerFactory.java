package wlsh.project.discordgames.catchmusic.infra.crawler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CrawlerFactory {

    private final MbcFM4UCrawler mbcFM4UCrawler;
    private final CBSCrawler cbsCrawler;

    public RadioCrawler getRadioCrawler(Radio radio) {
        if (radio instanceof MBCRadio) {
            return mbcFM4UCrawler;
        } else if (radio instanceof CBSRadio) {
            return cbsCrawler;
        }
        throw new RuntimeException("없음");
    }
}
