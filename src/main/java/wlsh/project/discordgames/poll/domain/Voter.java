package wlsh.project.discordgames.poll.domain;

public record Voter(
        String memberId,
        String name,
        boolean approve
) {
}
