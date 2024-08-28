package wlsh.project.discordgames.catchmusic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchmusic.domain.Round;
import wlsh.project.discordgames.discord.AudioPlayerService;

@Service
@Transactional
@RequiredArgsConstructor
public class PlayService {

    private final CatchMusicRepository catchMusicRepository;
    private final AudioPlayerService audioPlayerService;

    public void play(String guildId) {
        CatchMusic catchMusic = catchMusicRepository.findByGuildId(guildId)
                .orElseThrow(() -> new RuntimeException("없음"));
        Round round = catchMusic.getCurrentRound();
        audioPlayerService.play(guildId, round.getMusic().name(), round.getMusic().artist());
    }
}
