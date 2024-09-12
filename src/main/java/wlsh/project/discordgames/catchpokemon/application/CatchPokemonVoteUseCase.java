package wlsh.project.discordgames.catchpokemon.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchmusic.application.dto.VoteResult;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.common.poll.domain.Poll;
import wlsh.project.discordgames.common.poll.domain.PollRepository;
import wlsh.project.discordgames.common.poll.domain.PollType;
import wlsh.project.discordgames.common.poll.domain.Voter;
import wlsh.project.discordgames.catchpokemon.application.dto.CatchPokemonSkipResponse;
import wlsh.project.discordgames.catchpokemon.infra.CatchPokemonRepository;

@Service
@RequiredArgsConstructor
public class CatchPokemonVoteUseCase {

    private final PollRepository pollRepository;
    private final CatchPokemonRepository catchPokemonRepository;
    private final CatchPokemonSkipService catchPokemonSkipService;

    public VoteResult vote(String memberId, CatchGameId catchGameId, String pollId) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new RuntimeException("없음"));
        poll.vote(new Voter(memberId));
        if (poll.isMajority()) {
            if (poll.getType().equals(PollType.STOP)) {
                catchPokemonRepository.delete(catchGameId);
                return new VoteResult(PollType.STOP, null);
            } else if (poll.getType().equals(PollType.SKIP)) {
                CatchPokemonSkipResponse skipResponse = catchPokemonSkipService.skip(catchGameId);
                return new VoteResult(PollType.SKIP, skipResponse);
            }
        }
        return VoteResult.VOTE;
    }
}
