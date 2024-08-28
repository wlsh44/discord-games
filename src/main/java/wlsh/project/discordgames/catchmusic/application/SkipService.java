package wlsh.project.discordgames.catchmusic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.catchmusic.application.dto.SkipResponse;
import wlsh.project.discordgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchmusic.domain.Round;
import wlsh.project.discordgames.catchmusic.infra.crawler.MbcFM4UCrawler;
import wlsh.project.discordgames.catchmusic.infra.crawler.Radio;
import wlsh.project.discordgames.discord.AudioPlayerService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SkipService {

    private final CatchMusicRepository catchMusicRepository;
    private final AudioPlayerService audioPlayerService;
    private final MbcFM4UCrawler mbcFM4UCrawler;

    public SkipResponse skip(String guildId) {
        CatchMusic catchMusic = catchMusicRepository.findByGuildIdForLock(guildId)
                .orElseThrow(() -> new RuntimeException("없음"));
        Round skippedRound = catchMusic.getCurrentRound();
        catchMusic.skipRound();
        if (catchMusic.isMoreRoundRequired()) {
            List<Music> musicList = mbcFM4UCrawler.crawl(Radio.valueOf(catchMusic.getTag().content()));
            catchMusic.addRounds(musicList);
        }
        Music music = catchMusic.getCurrentRound().getMusic();
        audioPlayerService.play(guildId, music.name(), music.artist());
        return new SkipResponse(skippedRound.getMusic().name(), skippedRound.getMusic().artist());
    }
}
