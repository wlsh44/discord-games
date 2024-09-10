package wlsh.project.discordgames.pokemon.ui;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.discord.util.DiscordMessageHandler;
import wlsh.project.discordgames.pokemon.domain.CatchPokemon;
import wlsh.project.discordgames.pokemon.infra.CatchPokemonRepository;
import wlsh.project.discordgames.pokemon.domain.CatchPokemonRound;

@Service
@RequiredArgsConstructor
public class CatchPokemonCurrentRoundHandler {

    private final CatchPokemonRepository catchPokemonRepository;
    private final DiscordMessageHandler messageHandler;

    public void show(CatchGameId catchGameId, MessageChannel messageChannel) {
        doCountdown(catchGameId.channelId());
        CatchPokemon catchPokemon = catchPokemonRepository.findById(catchGameId)
                .orElseThrow(() -> new RuntimeException("없음"));
        CatchPokemonRound currentRound = catchPokemon.getCurrentRound();
        MessageEmbed messageEmbed = new EmbedBuilder()
                .setImage(currentRound.getPokemon().getImage())
                .build();
        messageChannel.sendMessageEmbeds(messageEmbed).queue();
    }

    private void doCountdown(String channelId) {
        try {
            int countdown = 5;
            messageHandler.sendMessage(channelId, "5초 뒤에 다음 라운드가 시작됩니다.");
            Thread.sleep(1000);
            String id = messageHandler.sendMessageWithId(channelId, String.valueOf(countdown--));
            while (countdown > 0) {
                Thread.sleep(1000);
                messageHandler.editMessage(channelId, id, String.valueOf(countdown--));
            }
            messageHandler.editMessage(channelId, id, "시작!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
