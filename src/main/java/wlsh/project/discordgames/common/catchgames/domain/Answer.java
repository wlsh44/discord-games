package wlsh.project.discordgames.common.catchgames.domain;

public record Answer(
        String main,
        String second
) {

    public boolean equals(String answer) {
        String playerAnswer = answer.toLowerCase().trim();
        return main.equals(playerAnswer) || second.equals(playerAnswer);
    }
}
