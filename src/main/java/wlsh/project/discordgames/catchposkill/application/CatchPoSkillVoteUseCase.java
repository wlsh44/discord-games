package wlsh.project.discordgames.catchposkill.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchmusic.application.dto.VoteResult;
import wlsh.project.discordgames.catchposkill.application.dto.CatchPoSkillSkipResponse;
import wlsh.project.discordgames.catchposkill.infra.CatchPoSkillRepository;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.common.poll.domain.Poll;
import wlsh.project.discordgames.common.poll.domain.PollRepository;
import wlsh.project.discordgames.common.poll.domain.PollType;
import wlsh.project.discordgames.common.poll.domain.Voter;

@Service
@RequiredArgsConstructor
public class CatchPoSkillVoteUseCase {

    private final PollRepository pollRepository;
    private final CatchPoSkillRepository catchPoSkillRepository;
    private final CatchPoSkillSkipService catchPoSkillSkipService;

    public VoteResult vote(String memberId, CatchGameId catchGameId, String pollId) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new RuntimeException("없음"));
        poll.vote(new Voter(memberId));
        if (poll.isMajority()) {
            if (poll.getType().equals(PollType.STOP)) {
                catchPoSkillRepository.delete(catchGameId);
                return new VoteResult(PollType.STOP, null);
            } else if (poll.getType().equals(PollType.SKIP)) {
                CatchPoSkillSkipResponse skipResponse = catchPoSkillSkipService.skip(catchGameId);
                return new VoteResult(PollType.SKIP, skipResponse);
            }
        }
        return VoteResult.VOTE;
    }
}
