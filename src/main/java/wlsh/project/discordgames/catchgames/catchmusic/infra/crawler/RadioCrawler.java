package wlsh.project.discordgames.catchgames.catchmusic.infra.crawler;

import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;

import java.util.List;

public interface RadioCrawler {
    List<Music> crawl(Radio radio);
}
