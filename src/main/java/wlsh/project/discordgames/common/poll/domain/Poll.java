package wlsh.project.discordgames.common.poll.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Poll {

    private String pollId;

    private String guildId;

    private PollType type;

    private String category;

    private List<Voter> voters = new ArrayList<>();

    private int participantCount;
    private boolean finished;

    public Poll(String pollId, String guildId, int participantCount, PollType type, String category) {
        this.pollId = pollId;
        this.guildId = guildId;
        this.participantCount = participantCount;
        this.category = category;
        this.type = type;
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
        long approveCount = voters.size();
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
