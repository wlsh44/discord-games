package wlsh.project.discordgames.pokemon.application;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import wlsh.project.discordgames.pokemon.domain.CatchPokemon;
import wlsh.project.discordgames.pokemon.domain.Pokemon;

public record CatchPokemonAnswerCorrectEvent(
        String guildId,
        String username,
        CatchPokemon catchPokemon,
        Pokemon pokemon,
        MessageChannel messageChannel

) {
}
