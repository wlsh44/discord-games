package wlsh.project.discordgames.catchmusic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchmusic.domain.Round;
import wlsh.project.discordgames.catchmusic.domain.Tag;
import wlsh.project.discordgames.catchmusic.infra.crawler.MbcFM4UCrawler;
import wlsh.project.discordgames.catchmusic.infra.crawler.Radio;
import wlsh.project.discordgames.discord.AudioPlayerService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateCatchMusicService {

    private final CatchMusicRepository catchMusicRepository;
    private final ApplicationEventPublisher publisher;
    private final AudioPlayerService audioPlayerService;
    private final MbcFM4UCrawler mbcFM4UCrawler;

    public void newCatchMusic(String guildId, Radio radio, int finishScore) {
        List<Music> musicList = mbcFM4UCrawler.crawl(radio);
        List<Round> rounds = new ArrayList<>();
        for (int roundNumber = 1; roundNumber <= musicList.size(); roundNumber++) {
            rounds.add(new Round(roundNumber, musicList.get(roundNumber - 1)));
        }
//        List<Round> rounds = List.of(
//                new Round(1, new Music("꿈의 거처", "이승윤")),
//                new Round(2, new Music("겨울잠", "아이유")),
//                new Round(3, new Music("photograph", "ed sheeran")),
//                new Round(4, new Music("enchanted", "taylor swift"))
//        );
        CatchMusic catchMusic = catchMusicRepository.save(CatchMusic.startGame(guildId, rounds, new Tag(radio.name()), finishScore));
        Music music = catchMusic.getCurrentRound().getMusic();
        audioPlayerService.play(guildId, music.name(), music.artist());
    }
}
