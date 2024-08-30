package wlsh.project.discordgames.pokemon.application;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.pokemon.domain.CatchPokemon;
import wlsh.project.discordgames.pokemon.domain.CatchPokemonRepository;
import wlsh.project.discordgames.pokemon.domain.Round2;

@Service
@RequiredArgsConstructor
public class CatchPokemonCurrentRoundService {

    private final CatchPokemonRepository catchPokemonRepository;

    public void show(String guildId, MessageChannel messageChannel) {
        CatchPokemon catchPokemon = catchPokemonRepository.get(guildId)
                .orElseThrow(() -> new RuntimeException("없음"));
        Round2 currentRound = catchPokemon.getCurrentRound();
        MessageEmbed messageEmbed = new EmbedBuilder()
                .setImage(currentRound.getPokemon().getImage())
                .build();
        messageChannel.sendMessageEmbeds(messageEmbed).queue();
    }
}
