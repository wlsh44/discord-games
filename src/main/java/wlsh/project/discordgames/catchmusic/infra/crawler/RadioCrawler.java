package wlsh.project.discordgames.catchmusic.infra.crawler;

import wlsh.project.discordgames.catchmusic.domain.Music;

import java.util.List;

public interface RadioCrawler {
    List<Music> crawl(Radio radio);
}
