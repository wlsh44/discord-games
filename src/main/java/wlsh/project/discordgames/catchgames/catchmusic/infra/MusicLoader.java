package wlsh.project.discordgames.catchgames.catchmusic.infra;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Album;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Artist;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchgames.catchmusic.infra.crawler.CrawlerFactory;
import wlsh.project.discordgames.catchgames.catchmusic.infra.crawler.MusicInfoCache;
import wlsh.project.discordgames.catchgames.catchmusic.infra.crawler.RadioCrawler;
import wlsh.project.discordgames.catchgames.catchmusic.infra.spotify.MusicInfo;
import wlsh.project.discordgames.catchgames.catchmusic.infra.spotify.SpotifySearchService;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MusicLoader {

    private final MusicInfoCache musicCache;
    private final CrawlerFactory crawlerFactory;
    private final SpotifySearchService spotifySearchService;

    public Music loadMusic(CatchMusic catchMusic) {
        if (catchMusic.getCurrentRoundNumber() == 0) {
            musicCache.clear();
        }
        if (musicCache.size() > 2) {
            return musicCache.get();
        }
        while (musicCache.size() <= 2) {
            RadioCrawler crawler = crawlerFactory.getRadioCrawler(catchMusic.getRadio());
            List<Music> musicList = crawler.crawl(catchMusic.getRadio());
            musicList.parallelStream()
                    .map(music -> {
                        try {
                            MusicInfo musicInfo = spotifySearchService.searchMusicInfo(music);
                            log.info("노래: {}, 가수: {}, 유명도: {}, 발매일: {}", music.name(), music.artist(), musicInfo.popularity(), musicInfo.releaseDate());
                            return Music.of(musicInfo.musicName(), music.secondName(), new Artist(musicInfo.artistName(), musicInfo.artistUrl()), new Album(musicInfo.albumName(), musicInfo.albumUrl()), musicInfo.popularity(), musicInfo.releaseDate());
                        } catch (Exception e) {
                            log.info("노래: {}, 가수: {}, 유명도: {}, 발매일: {}", music.name(), music.artist(), null, null);
                            return music;
                        }
                    })
                    .filter(music -> catchMusic.getFilterOptions().stream()
                            .allMatch(filterOption -> filterOption.doFilter(music)))
                    .forEach(musicCache::save);
        }
        return musicCache.get();
    }
}
