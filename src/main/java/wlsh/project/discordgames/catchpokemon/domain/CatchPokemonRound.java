package wlsh.project.discordgames.catchpokemon.domain;

import lombok.Getter;
import wlsh.project.discordgames.common.catchgames.domain.Answer;
import wlsh.project.discordgames.common.catchgames.domain.Round;

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
