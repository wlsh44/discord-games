package wlsh.project.discordgames.pokemon.domain;

public record Answerer(
        String answererId,
        String name
) {
    public static Answerer of(Player player) {
        return new Answerer(player.id(), player.name());
    }
}
