package wlsh.project.discordgames.catchmusic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchmusic.domain.CatchMusicRound;
import wlsh.project.discordgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchmusic.infra.MusicLoader;

@Service
@RequiredArgsConstructor
public class AddCatchMusicRoundService {

    private final MusicLoader musicLoader;

    public void addRound(CatchMusic catchMusic) {
        Music music = musicLoader.loadMusic(catchMusic);
        catchMusic.updateNewRound(CatchMusicRound.prototype(music));
    }
}
