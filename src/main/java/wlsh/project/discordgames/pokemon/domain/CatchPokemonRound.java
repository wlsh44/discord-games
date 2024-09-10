package wlsh.project.discordgames.pokemon.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import wlsh.project.discordgames.catchmusic.domain.CatchMusicRound;
import wlsh.project.discordgames.catchmusic.domain.Music;
import wlsh.project.discordgames.common.catchgames.domain.Answer;
import wlsh.project.discordgames.common.catchgames.domain.Round;

import java.util.Objects;

@Getter
public class CatchPokemonRound extends Round {

    private Pokemon pokemon;

    public CatchPokemonRound(int roundNumber, Pokemon pokemon) {
        super(roundNumber, makeAnswer(pokemon));
        this.pokemon = pokemon;
    }

    public static CatchPokemonRound prototype(Pokemon pokemon) {
        return new CatchPokemonRound(1, pokemon);
    }

    @Override
    protected Round createWithPrototype(int roundNumber) {
        return new CatchPokemonRound(roundNumber, pokemon);
    }

    public static Answer makeAnswer(Pokemon pokemon) {
        String name = pokemon.getKo()
                .toLowerCase();
        String regex = "\\([^()]*\\)";

        while (name.matches(".*\\([^()]*\\).*")) {
            name = name.replaceAll(regex, "").trim();
        }
        String koAnswer = name.trim();
        String enAnswer = pokemon.getEn()
                .toLowerCase().trim();
        return new Answer(koAnswer, enAnswer);
    }
}
