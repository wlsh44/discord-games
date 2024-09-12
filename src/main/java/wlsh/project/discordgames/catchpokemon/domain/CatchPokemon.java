package wlsh.project.discordgames.catchpokemon.domain;

import lombok.Getter;
import wlsh.project.discordgames.common.catchgames.domain.CatchGame;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;

import java.util.List;

@Getter
public class CatchPokemon extends CatchGame {

    private List<PokemonFilterOption> pokemonFilterOptions;

    public CatchPokemon(CatchGameId catchGameId, int currentRoundNumber, List<PokemonFilterOption> pokemonFilterOptions, int finishScore) {
        super(catchGameId, currentRoundNumber, finishScore);
        this.pokemonFilterOptions = pokemonFilterOptions;
    }

    public static CatchPokemon startGame(CatchGameId catchGameId, int finishScore, List<PokemonFilterOption> pokemonFilterOptions) {
        return new CatchPokemon(catchGameId, 0, pokemonFilterOptions, finishScore);
    }

    public CatchPokemonRound getCurrentRound() {
        return (CatchPokemonRound) rounds.get(currentRoundNumber - 1);
    }

}
