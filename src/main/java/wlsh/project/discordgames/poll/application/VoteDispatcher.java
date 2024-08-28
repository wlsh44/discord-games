package wlsh.project.discordgames.poll.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.EmojiUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.poll.MessagePollVoteAddEvent;
import net.dv8tion.jda.api.events.message.poll.MessagePollVoteRemoveEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import wlsh.project.discordgames.catchmusic.application.AnswerService;
import wlsh.project.discordgames.catchmusic.application.PlayService;
import wlsh.project.discordgames.catchmusic.application.SkipService;
import wlsh.project.discordgames.catchmusic.application.dto.SkipResponse;
import wlsh.project.discordgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchmusic.domain.CatchMusicRepository;
import wlsh.project.discordgames.poll.domain.Poll;
import wlsh.project.discordgames.poll.domain.PollCategory;
import wlsh.project.discordgames.poll.domain.PollRepository;
import wlsh.project.discordgames.poll.domain.Voter;

import javax.annotation.Nonnull;

import java.util.List;

import static wlsh.project.discordgames.catchmusic.ChannelValidator.checkValidChannelState;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class VoteDispatcher extends ListenerAdapter {

    private final AnswerService answerService;
    private final PlayService playService;
    private final PollRepository pollRepository;
    private final SkipService skipService;
    private final CatchMusicRepository catchMusicRepository;

    @Override
    public void onMessagePollVoteAdd(@Nonnull MessagePollVoteAddEvent event) {
        String id = event.getMessageId();
        System.out.println("VoteDispatcher.onMessagePollVoteAdd");
        System.out.println("event.getMessageId() = " + event.getMessageId());
        Poll poll = pollRepository.findById(event.getMessageId())
                .orElseThrow(() -> new RuntimeException("없음"));
        poll.vote(new Voter(event.getUserId(), null, true));
        if (poll.isMajority()) {
            CatchMusic catchMusic = catchMusicRepository.findByGuildId(event.getGuild().getId())
                    .orElseThrow(() -> new RuntimeException("없는 게임"));
            if (poll.getCategory().equals(PollCategory.STOP)) {
                catchMusicRepository.delete(catchMusic);
                event.getChannel().sendMessage("게임이 중단되었습니다").queue();
            } else if (poll.getCategory().equals(PollCategory.SKIP)) {
                SkipResponse skip = skipService.skip(event.getGuild().getId());
                event.getChannel().sendMessage("스킵되었습니다. 정답 %s - %s".formatted(skip.musicName(), skip.artist())).queue();
            }
        }
    }

    @Override
    public void onMessagePollVoteRemove(@Nonnull MessagePollVoteRemoveEvent event) {
        Poll poll = pollRepository.findById(event.getMessageId())
                .orElseThrow(() -> new RuntimeException("없음"));
        Voter voter = new Voter(event.getUserId(), null, true);
        poll.cancelVote(voter);
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.getMessage().getPoll() == null) {
            return;
        }
        String id = event.getMessageId();
        String text = event.getMessage().getPoll().getQuestion().getText();
        int size = event.getMember().getVoiceState().getChannel().getMembers().size() - 1;
        PollCategory category = null;
        System.out.println("text = " + text);
        if ("게임 중단 투표".equals(text)) {
            category = PollCategory.STOP;
        } else if ("노래 스킵 투표".equals(text)) {
            category = PollCategory.SKIP;
        }
        Poll poll = new Poll(event.getMessageId(), event.getGuild().getId(), size, category);
        pollRepository.save(event.getMessageId(), poll);
    }

}
