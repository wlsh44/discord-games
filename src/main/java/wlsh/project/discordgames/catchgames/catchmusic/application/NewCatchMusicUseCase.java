package wlsh.project.discordgames.catchgames.catchmusic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchgames.catchmusic.infra.crawler.Radio;
import wlsh.project.discordgames.catchgames.common.catchgames.domain.CatchGameId;

@Service
@RequiredArgsConstructor
public class NewCatchMusicUseCase {

    private final CatchMusicRepository catchMusicRepository;
    private final AddCatchMusicRoundService addCatchMusicRoundService;

    public CatchMusic newCatchMusic(CatchGameId catchGameId, Radio radio, int finishScore) {
        CatchMusic catchMusic = catchMusicRepository.save(CatchMusic.startGame(catchGameId, radio, finishScore));

        addCatchMusicRoundService.addRound(catchMusic);

        return catchMusic;
    }
}
