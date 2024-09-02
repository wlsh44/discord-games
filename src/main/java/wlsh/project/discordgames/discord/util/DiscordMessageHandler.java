package wlsh.project.discordgames.discord.util;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import wlsh.project.discordgames.discord.ChannelRepository;

@Component
@RequiredArgsConstructor
public class DiscordMessageHandler {

    private final ChannelRepository channelRepository;

    public void sendEmbedMessage(MessageChannel channel, String title, String... descriptions) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        if (StringUtils.hasText(title)) {
            embedBuilder.setTitle(title);
        }
        for (String description : descriptions) {
            embedBuilder.appendDescription(description);
        }
        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }

    public void sendMessage(MessageChannel channel, String message) {
        channel.sendMessage(message).queue();
    }

    public void sendMessage(String channelId, String message) {
        MessageChannel channel = channelRepository.get(channelId);
        channel.sendMessage(message).queue();
    }

    public String sendMessageWithId(String channelId, String message) {
        MessageChannel channel = channelRepository.get(channelId);
        Message sentMessage = channel.sendMessage(message).complete();
        return sentMessage.getId();
    }

    public void sendReply(String guildId, String message) {
        MessageChannel channel = channelRepository.get(guildId);
        channel.sendMessage(message).queue();
    }

    public void sendEmbedMessage(String channelId, String title, String... descriptions) {
        MessageChannel channel = channelRepository.get(channelId);
        EmbedBuilder embedBuilder = new EmbedBuilder();
        if (StringUtils.hasText(title)) {
            embedBuilder.setTitle(title);
        }
        for (String description : descriptions) {
            embedBuilder.appendDescription(description);
        }
        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }

    public void editMessage(String channelId, String messageId, String message) {
        MessageChannel channel = channelRepository.get(channelId);
        channel.editMessageById(messageId, message).queue();
    }
}
