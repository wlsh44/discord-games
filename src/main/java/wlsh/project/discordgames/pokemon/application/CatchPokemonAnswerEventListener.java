package wlsh.project.discordgames.pokemon.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.discord.ChannelRepository;
import wlsh.project.discordgames.discord.util.DiscordMessageHandler;
import wlsh.project.discordgames.pokemon.domain.CatchPokemonRepository;

@Component
@RequiredArgsConstructor
public class CatchPokemonAnswerEventListener {

    private final ChannelRepository channelRepository;
    private final CatchPokemonStatusService catchPokemonStatusService;
    private final DiscordMessageHandler messageHandler;
    private final CatchPokemonRepository catchPokemonRepository;
    private final CatchPokemonCurrentRoundService catchPokemonCurrentRoundService;

    @EventListener
    public void handleAnswerCorrect(CatchPokemonAnswerCorrectEvent event) {
        String guildId = event.guildId();
        messageHandler.sendEmbedMessage(
                guildId,
                null,
                "**정답** : `%s`\n".formatted(event.username()),
                "`%s(%s)`\n".formatted(event.pokemon().getKo(), event.pokemon().getEn()),
                "`%d 세대`\n".formatted(event.pokemon().getGeneration())
//                "**Source:** `%s\n`".formatted(audioTrackInfo.title),
//                "**URL:** `%s\n`".formatted(audioTrackInfo.uri)
        );
        catchPokemonStatusService.sendStatus(guildId);
        if (event.catchPokemon().isFinished()) {
            handleCatchMusicFinished(guildId);
            catchPokemonRepository.delete(guildId);
            return;
        }
        doCountdown(guildId);
//        Music music = event.catchPokemon().getCurrentRound().get();
//        audioPlayerService.play(guildId, music.name(), music.artist());
        catchPokemonCurrentRoundService.show(guildId, event.messageChannel());
    }

    private void doCountdown(String guildId) {
        try {
            int countdown = 5;
            messageHandler.sendMessage(guildId, "5초 뒤에 다음 라운드가 시작됩니다.");
            Thread.sleep(1000);
            String id = messageHandler.sendMessageWithId(guildId, String.valueOf(countdown--));
            while (countdown > 0) {
                Thread.sleep(1000);
                messageHandler.editMessage(guildId, id, String.valueOf(countdown--));
            }
            messageHandler.editMessage(guildId, id, "시작!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleCatchMusicFinished(String guildId) {
        messageHandler.sendMessage(guildId, "끝");
    }
}
