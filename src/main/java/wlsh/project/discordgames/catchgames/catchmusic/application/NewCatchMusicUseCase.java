package wlsh.project.discordgames.catchgames.catchmusic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchgames.catchmusic.domain.PopularityFilter;
import wlsh.project.discordgames.catchgames.catchmusic.domain.YearFilter;
import wlsh.project.discordgames.catchgames.catchmusic.infra.crawler.Radio;
import wlsh.project.discordgames.catchgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.catchgames.catchmusic.domain.FilterOption;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewCatchMusicUseCase {

    private final CatchMusicRepository catchMusicRepository;
    private final AddCatchMusicRoundService addCatchMusicRoundService;

    public CatchMusic newCatchMusic(CatchGameId catchGameId, Radio radio, int finishScore, int popularity, int year) {
        List<FilterOption> filterOptions = List.of(
                new PopularityFilter(popularity),
                new YearFilter(year)
        );
        CatchMusic catchMusic = catchMusicRepository.save(CatchMusic.startGame(catchGameId, finishScore, filterOptions, radio));

        addCatchMusicRoundService.addRound(catchMusic);

        return catchMusic;
    }
}
