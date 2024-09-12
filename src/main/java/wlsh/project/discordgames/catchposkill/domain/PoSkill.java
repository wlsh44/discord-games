package wlsh.project.discordgames.catchposkill.domain;

public record PoSkill(
        String id,
        String koName,
        String enName,
        String type,
        String attack,
        String description,
        String damage,
        String accuracy,
        String pp,
        String generation
) {
}
