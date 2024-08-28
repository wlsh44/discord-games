package wlsh.project.discordgames.catchmusic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.catchmusic.application.dto.AnswerResult;
import wlsh.project.discordgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchmusic.domain.Player;
import wlsh.project.discordgames.catchmusic.domain.Round;
import wlsh.project.discordgames.catchmusic.infra.crawler.MbcFM4UCrawler;
import wlsh.project.discordgames.catchmusic.infra.crawler.Radio;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerService {

    private final CatchMusicRepository catchMusicRepository;
    private final ApplicationEventPublisher publisher;
    private final MbcFM4UCrawler mbcFM4UCrawler;

    public void answer(String guildId, Player player) {
        Optional<CatchMusic> catchMusicOptional = catchMusicRepository.findByGuildIdForLock(guildId);
        if (catchMusicOptional.isEmpty()) {
            return;
        }
        CatchMusic catchMusic = catchMusicOptional.get();
        Music currentMusic = catchMusic.getCurrentRound().getMusic();
        boolean result = catchMusic.answer(player);

        if (result) {
            if (catchMusic.isMoreRoundRequired()) {
                List<Music> musicList = mbcFM4UCrawler.crawl(Radio.valueOf(catchMusic.getTag().content()));
                catchMusic.addRounds(musicList);
            }
            publisher.publishEvent(new AnswerCorrectEvent(guildId, player.name(), catchMusic, currentMusic));
        }
    }
}
