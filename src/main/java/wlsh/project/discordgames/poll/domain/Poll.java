package wlsh.project.discordgames.poll.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Poll {

    private String pollId;

    private String guildId;

    private PollCategory category;

    private List<Voter> voters = new ArrayList<>();

    private int participantCount;
    private boolean finished;

    public Poll(String pollId, String guildId, int participantCount, PollCategory category) {
        this.pollId = pollId;
        this.guildId = guildId;
        this.participantCount = participantCount;
        this.category = category;
    }

    public void vote(Voter voter) {
        if (finished) {
            return;
        }
        voters.add(voter);
    }

    public boolean isMajority() {
        if (finished) {
            return false;
        }
        long approveCount = voters.stream()
                .filter(Voter::approve)
                .count();
        boolean res = approveCount > participantCount / 2;
        this.finished = res;
        return res;
    }

    public void cancelVote(Voter voter) {
        if (finished) {
            return;
        }
        voters.remove(voter);
    }
}
