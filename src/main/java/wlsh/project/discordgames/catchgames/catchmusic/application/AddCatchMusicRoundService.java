package wlsh.project.discordgames.catchgames.catchmusic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusicRound;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchgames.catchmusic.infra.crawler.MbcFM4UCrawler;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddCatchMusicRoundService {

    private final MbcFM4UCrawler mbcFM4UCrawler;

    public void addRound(CatchMusic catchMusic) {
        List<Music> musicList = mbcFM4UCrawler.crawl(catchMusic.getRadio());
        catchMusic.updateNewRound(CatchMusicRound.prototype(musicList.get(0)));
    }
}
