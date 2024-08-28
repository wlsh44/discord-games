package wlsh.project.discordgames.catchmusic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchmusic.domain.Round;
import wlsh.project.discordgames.discord.util.DiscordMessageHandler;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CatchMusicStatusService {

    private final CatchMusicRepository catchMusicRepository;
    private final DiscordMessageHandler messageHandler;

    public void sendStatus(String guildId) {
        CatchMusic catchMusic = catchMusicRepository.findByGuildId(guildId)
                .orElseThrow(() -> new RuntimeException("없음"));

        List<Round> rounds = catchMusic.getRounds();
        int currentRound = catchMusic.getCurrentRoundNumber();
        String roundStatus = "`%d 라운드`".formatted(currentRound - 1);
        String finishScore = "`%d 점`".formatted(catchMusic.getFinishScore());
        String scoreStatus = rounds.stream()
                .filter(Round::isFinished)
                .collect(Collectors.groupingBy(
                        round -> round.getAnswerer().name(),
                        Collectors.mapping(
                                Round::getAnswerer,
                                Collectors.counting())
                )).entrySet().stream()
                .map(status -> "`%s : %d점`".formatted(status.getKey(), status.getValue()))
                .collect(Collectors.joining("\n"));

        messageHandler.sendEmbedMessage(
                guildId,
                "통계",
                "**Round** : %s\n".formatted(roundStatus),
                "**목표 점수** : %s\n\n".formatted(finishScore),
                "**점수**\n",
                scoreStatus
        );
//        return new CatchMusicStatus(roundStatus, scoreStatus);
    }
}
