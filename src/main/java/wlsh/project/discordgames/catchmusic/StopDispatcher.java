package wlsh.project.discordgames.catchmusic;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.emoji.EmojiUnion;
import net.dv8tion.jda.api.entities.messages.MessagePoll;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.messages.MessagePollData;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchmusic.application.SkipService;
import wlsh.project.discordgames.catchmusic.application.dto.SkipResponse;
import wlsh.project.discordgames.discord.command.ICommand;
import wlsh.project.discordgames.discord.util.DiscordMessageHandler;

import java.time.Duration;
import java.util.List;

import static wlsh.project.discordgames.catchmusic.ChannelValidator.checkValidChannelState;

@Component
@RequiredArgsConstructor
public class StopDispatcher implements ICommand {

    //    private final StopService stopService;
    private final DiscordMessageHandler messageHandler;

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getDescription() {
        return "Stop Game";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of();
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        checkValidChannelState(event.getMember(), event.getGuild(), event.getChannel());

//        String id = messageHandler.sendMessageWithId(event.getGuild().getId(), "투표");
        MessageChannelUnion channel = event.getChannel();
        MessageEmbed embed = new EmbedBuilder().setTitle("투표").build();
//        Message sentMessage = channel.sendMessageEmbeds(embed).complete();
//        Message message = channel.sendMessagePoll(new MessagePollData(
//                        MessagePoll.LayoutType.DEFAULT,
//                        new MessagePoll.Question("찬", Emoji.fromUnicode("👍")),
//                        List.of(new MessagePoll.Answer(1L, "찬", new EmojiUnion(), 1, false)),
//                        Duration.ofSeconds(120),
//                        false
//                ))
//                .complete();
        Message message = channel.sendMessage("게임 중단 투표")
                .setPoll(
                        MessagePollData.builder("게임 중단 투표")
                                .addAnswer("찬성", Emoji.fromUnicode("👍"))
                                .setMultiAnswer(false)
                                .build())
                .complete();
//        message.pin().queue();
        MessagePoll poll = message.getPoll();
//        sentMessage.addReaction(Emoji.fromUnicode("👍")).queue();
//        sentMessage.addReaction(Emoji.fromUnicode("👎")).queue();
//        String id = sentMessage.getId();
        System.out.println("투표 id = " + message);
        System.out.println("poll = " + poll);
    }
}
