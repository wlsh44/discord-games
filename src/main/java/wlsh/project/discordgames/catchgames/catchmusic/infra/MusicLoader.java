package wlsh.project.discordgames.catchgames.catchmusic.infra;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Album;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Artist;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchgames.catchmusic.infra.crawler.MbcFM4UCrawler;
import wlsh.project.discordgames.catchgames.catchmusic.infra.crawler.MusicInfoCache;
import wlsh.project.discordgames.catchgames.catchmusic.infra.spotify.MusicInfo;
import wlsh.project.discordgames.catchgames.catchmusic.infra.spotify.SpotifySearchService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MusicLoader {

    private final MusicInfoCache musicCache;
    private final MbcFM4UCrawler crawler;
    private final SpotifySearchService spotifySearchService;

    public Music loadMusic(CatchMusic catchMusic, int popularity) {
        if (catchMusic.getCurrentRoundNumber() == 0) {
            musicCache.clear();
        }
        if (musicCache.size() > 2) {
            return musicCache.get();
        }
        while (musicCache.size() <= 2) {
            List<Music> musicList = crawler.crawl(catchMusic.getRadio());
            musicList.parallelStream()
                    .map(music -> {
                        try {
                            MusicInfo musicInfo = spotifySearchService.searchMusicInfo(music);
                            log.info("노래: {}, 가수: {}, 유명도: {}, 발매일: {}", music.name(), music.artist(), musicInfo.popularity(), musicInfo.releaseDate());
                            return Music.of(music.name(), music.secondName(), new Artist(musicInfo.artistName(), musicInfo.artistUrl()), new Album(musicInfo.albumName(), musicInfo.albumUrl()), musicInfo.popularity(), musicInfo.releaseDate());
                        } catch (Exception e) {
                            log.info("노래: {}, 가수: {}, 유명도: {}, 발매일: {}", music.name(), music.artist(), null, null);
                            return music;
                        }
                    })
                    .filter(music -> music.popularity() >= popularity)
                    .forEach(musicCache::save);
        }
        return musicCache.get();
    }
}
