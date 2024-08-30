package wlsh.project.discordgames.pokemon.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.discord.util.DiscordMessageHandler;
import wlsh.project.discordgames.pokemon.domain.CatchPokemon;
import wlsh.project.discordgames.pokemon.domain.CatchPokemonRepository;
import wlsh.project.discordgames.pokemon.domain.Round2;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CatchPokemonStatusService {

    private final CatchPokemonRepository catchPokemonRepository;
    private final DiscordMessageHandler messageHandler;

    public void sendStatus(String guildId) {
        CatchPokemon catchPokemon = catchPokemonRepository.get(guildId)
                .orElseThrow(() -> new RuntimeException("없음"));

        List<Round2> rounds = catchPokemon.getRounds();
        int currentRound = catchPokemon.getCurrentRoundNumber();
        String roundStatus = "`%d 라운드`".formatted(currentRound - 1);
        String finishScore = "`%d 점`".formatted(catchPokemon.getFinishScore());
        String scoreStatus = rounds.stream()
                .filter(Round2::isFinished)
                .collect(Collectors.groupingBy(
                        round -> round.getAnswerer().name(),
                        Collectors.mapping(
                                Round2::getAnswerer,
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
