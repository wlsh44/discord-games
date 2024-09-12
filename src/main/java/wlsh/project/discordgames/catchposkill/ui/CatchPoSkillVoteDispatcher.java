package wlsh.project.discordgames.catchposkill.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.poll.MessagePollVoteAddEvent;
import net.dv8tion.jda.api.events.message.poll.MessagePollVoteRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchmusic.application.dto.VoteResult;
import wlsh.project.discordgames.catchposkill.application.CatchPoSkillVoteUseCase;
import wlsh.project.discordgames.catchposkill.application.dto.CatchPoSkillSkipResponse;
import wlsh.project.discordgames.catchposkill.domain.PoSkill;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.common.poll.domain.Poll;
import wlsh.project.discordgames.common.poll.domain.PollRepository;
import wlsh.project.discordgames.common.poll.domain.PollType;
import wlsh.project.discordgames.common.poll.domain.Voter;

import javax.annotation.Nonnull;

@Slf4j
@Component
@RequiredArgsConstructor
public class CatchPoSkillVoteDispatcher extends ListenerAdapter {

    private final PollRepository pollRepository;
    private final CatchPoSkillVoteUseCase catchPoSkillVoteUseCase;
    private final CatchPoSkillCurrentRoundHandler catchPoSkillCurrentRoundHandler;

    @Override
    public void onMessagePollVoteAdd(@Nonnull MessagePollVoteAddEvent event) {
        if (!"캐치포스킬".equals(event.getChannel().getName())) {
            return;
        }
        CatchGameId catchGameId = new CatchGameId(event.getGuild().getId(), event.getChannel().getId());
        VoteResult voteResult = catchPoSkillVoteUseCase.vote(event.getUserId(), catchGameId, event.getMessageId());
        switch (voteResult.pollType()) {
            case STOP -> event.getChannel().sendMessage("게임이 중단되었습니다").queue();
            case SKIP -> {
                CatchPoSkillSkipResponse response = (CatchPoSkillSkipResponse) voteResult.content();
                PoSkill poskill = response.poSkill();
                event.getChannel().sendMessage("스킵되었습니다. 정답 %s(%s) - %s 세대".formatted(poskill.koName(), poskill.enName(), poskill.generation())).queue();
                catchPoSkillCurrentRoundHandler.show(catchGameId, event.getChannel());
            }
        }
    }

    @Override
    public void onMessagePollVoteRemove(@Nonnull MessagePollVoteRemoveEvent event) {
        if (!"캐치포스킬".equals(event.getChannel().getName())) {
            return;
        }
        Poll poll = pollRepository.findById(event.getMessageId())
                .orElseThrow(() -> new RuntimeException("없음"));
        Voter voter = new Voter(event.getUserId());
        poll.cancelVote(voter);
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (!"캐치포스킬".equals(event.getChannel().getName())) {
            return;
        }
        if (event.getMessage().getPoll() == null) {
            return;
        }
        String text = event.getMessage().getPoll().getQuestion().getText();
//        int size = event.getMember().getVoiceState().getChannel().getMembers().size() - 1;
        int size = 1;
        PollType type = null;
        if ("게임 중단 투표".equals(text)) {
            type = PollType.STOP;
        } else if ("스킵 투표".equals(text)) {
            type = PollType.SKIP;
        }
        Poll poll = new Poll(event.getMessageId(), event.getGuild().getId(), size, type, event.getChannel().getName());
        pollRepository.save(event.getMessageId(), poll);
    }

}
