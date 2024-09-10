package wlsh.project.discordgames.common.poll.ui;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.messages.MessagePollData;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchmusic.ui.ChannelValidator;
import wlsh.project.discordgames.discord.command.ICommand;

import java.time.Duration;
import java.util.List;


@Component
@RequiredArgsConstructor
public class SkipDispatcher implements ICommand {

    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getDescription() {
        return "스킵";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of();
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if ("캐치뮤직".equals(event.getChannel().getName())) {
            ChannelValidator.checkValidChannelState(event.getMember(), event.getGuild(), event.getChannel());
        } else if (!"캐치포켓몬".equals(event.getChannel().getName())) {
            return;
        }

        MessageChannelUnion channel = event.getChannel();
        channel.sendMessage("스킵 투표가 생성되었습니다.")
                .setPoll(
                        MessagePollData.builder("스킵 투표")
                                .addAnswer("찬성", Emoji.fromUnicode("👍"))
                                .setMultiAnswer(false)
                                .setDuration(Duration.ofHours(1))
                                .build())
                .complete();
    }
}
