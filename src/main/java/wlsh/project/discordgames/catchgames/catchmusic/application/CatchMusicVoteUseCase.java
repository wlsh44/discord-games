package wlsh.project.discordgames.catchgames.catchmusic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchgames.catchmusic.application.dto.SkipResponse;
import wlsh.project.discordgames.catchgames.catchmusic.application.dto.VoteResult;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.catchgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.catchgames.common.poll.domain.Poll;
import wlsh.project.discordgames.catchgames.common.poll.domain.PollRepository;
import wlsh.project.discordgames.catchgames.common.poll.domain.PollType;
import wlsh.project.discordgames.catchgames.common.poll.domain.Voter;

@Service
@RequiredArgsConstructor
public class CatchMusicVoteUseCase {

    private final PollRepository pollRepository;
    private final CatchMusicRepository catchMusicRepository;
    private final CatchMusicSkipService catchMusicSkipService;

    public VoteResult vote(String memberId, CatchGameId catchGameId, String pollId) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new RuntimeException("없음"));
        poll.vote(new Voter(memberId));
        if (poll.isMajority()) {
            if (poll.getType().equals(PollType.STOP)) {
                catchMusicRepository.delete(catchGameId);
                return new VoteResult(PollType.STOP, null);
            } else if (poll.getType().equals(PollType.SKIP)) {
                SkipResponse skipResponse = catchMusicSkipService.skip(catchGameId);
                return new VoteResult(PollType.SKIP, skipResponse);
            }
        }
        return VoteResult.VOTE;
    }
}
