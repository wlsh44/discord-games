package wlsh.project.discordgames.catchmusic;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.messages.MessagePoll;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.messages.MessagePollData;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchmusic.application.SkipService;
import wlsh.project.discordgames.catchmusic.application.dto.SkipResponse;
import wlsh.project.discordgames.discord.command.ICommand;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static wlsh.project.discordgames.catchmusic.ChannelValidator.checkValidChannelState;

@Component
@RequiredArgsConstructor
public class SkipDispatcher implements ICommand {

    private final SkipService skipService;

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
        checkValidChannelState(event.getMember(), event.getGuild(), event.getChannel());

        MessageChannelUnion channel = event.getChannel();
        Message message = channel.sendMessage("Hello guys! Check my poll:")
                .setPoll(
                        MessagePollData.builder("노래 스킵 투표")
                                .addAnswer("찬성", Emoji.fromUnicode("👍"))
                                .setMultiAnswer(false)
                                .build())
                .complete();
//        message.pin().queue();
        MessagePoll poll = message.getPoll();

//        SkipResponse skip = skipService.skip(event.getGuild().getId());
//        event.reply("스킵되었습니다. 정답 %s - %s".formatted(skip.musicName(), skip.artist())).queue();
    }
}
