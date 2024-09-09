package wlsh.project.discordgames.catchmusic.ui;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.messages.MessagePollData;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.discord.command.ICommand;

import java.time.Duration;
import java.util.List;


@Component
@RequiredArgsConstructor
public class CatchMusicSkipDispatcher implements ICommand {

//    private final SkipService skipService;

    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getDescription() {
        return "Create New Game";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of();
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        ChannelValidator.checkValidChannelState(event.getMember(), event.getGuild(), event.getChannel());

        MessageChannelUnion channel = event.getChannel();
        Message message = channel.sendMessage("노래 스킵 투표가 생성되었습니다.")
                .setPoll(
                        MessagePollData.builder("스킵 투표")
                                .addAnswer("찬성", Emoji.fromUnicode("👍"))
                                .setMultiAnswer(false)
                                .setDuration(Duration.ofHours(1))
                                .build())
                .complete();
//        message.pin().queue();
//        MessagePoll poll = message.getPoll();
//        SkipResponse skip = skipService.skip(event.getGuild().getId());
//        event.reply("스킵되었습니다. 정답 %s - %s".formatted(skip.musicName(), skip.artist())).queue();
    }
}
