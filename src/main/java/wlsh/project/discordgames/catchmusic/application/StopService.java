package wlsh.project.discordgames.catchmusic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.catchmusic.domain.CatchMusicRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class StopService {

    private final CatchMusicRepository catchMusicRepository;

    public void stop(String guildId) {

    }
}
