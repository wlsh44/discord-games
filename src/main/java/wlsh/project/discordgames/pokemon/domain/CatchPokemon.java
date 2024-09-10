package wlsh.project.discordgames.pokemon.domain;

import lombok.Getter;
import wlsh.project.discordgames.common.catchgames.domain.CatchGame;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;

import java.util.Collections;
import java.util.List;

@Getter
public class CatchPokemon extends CatchGame {

    private List<FilterOption> filterOptions;

    public CatchPokemon(CatchGameId catchGameId, int currentRoundNumber, List<FilterOption> filterOptions, int finishScore) {
        super(catchGameId, currentRoundNumber, finishScore);
        this.filterOptions = filterOptions;
    }

    public static CatchPokemon startGame(CatchGameId catchGameId, int finishScore, List<FilterOption> filterOptions) {
        return new CatchPokemon(catchGameId, 0, filterOptions, finishScore);
    }

    public CatchPokemonRound getCurrentRound() {
        return (CatchPokemonRound) rounds.get(currentRoundNumber - 1);
    }

}
