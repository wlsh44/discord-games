package wlsh.project.discordgames.pokemon.application.dto;

import wlsh.project.discordgames.common.catchgames.application.dto.CatchGameStatus;
import wlsh.project.discordgames.pokemon.domain.Pokemon;

public record CatchPokemonAnswerResult(
        Status status,
        Object content
) {

    public static final CatchPokemonAnswerResult INCORRECT = new CatchPokemonAnswerResult(Status.INCORRECT, null);

    public static CatchPokemonAnswerResult correct(Pokemon pokemon, CatchGameStatus status) {
        return new CatchPokemonAnswerResult(Status.CORRECT, new CorrectContent(pokemon, status));
    }

    public static CatchPokemonAnswerResult finish(CatchGameStatus status) {
        return new CatchPokemonAnswerResult(Status.FINISH, new FinishContent(status));
    }

    public enum Status {
        INCORRECT, CORRECT, FINISH
    }

    public record CorrectContent(
            Pokemon pokemon,
            CatchGameStatus status
    ) { }

    public record FinishContent(
            CatchGameStatus status
    ) {
    }

}
