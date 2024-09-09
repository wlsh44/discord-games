package wlsh.project.discordgames.catchgames.catchmusic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusicRound;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchgames.catchmusic.infra.MusicLoader;
import wlsh.project.discordgames.catchgames.catchmusic.infra.crawler.MbcFM4UCrawler;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddCatchMusicRoundService {

    private final MusicLoader musicLoader;

    public void addRound(CatchMusic catchMusic) {
        Music music = musicLoader.loadMusic(catchMusic);
        catchMusic.updateNewRound(CatchMusicRound.prototype(music));
    }
}
