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

    public void sendMessage(String guildId, String message) {
        MessageChannel channel = channelRepository.get(guildId);
        channel.sendMessage(message).queue();
    }

    public String sendMessageWithId(String guildId, String message) {
        MessageChannel channel = channelRepository.get(guildId);
        Message sentMessage = channel.sendMessage(message).complete();
        return sentMessage.getId();
    }

    public void sendReply(String guildId, String message) {
        MessageChannel channel = channelRepository.get(guildId);
        channel.sendMessage(message).queue();
    }

    public void sendEmbedMessage(String guildId, String title, String... descriptions) {
        MessageChannel channel = channelRepository.get(guildId);
        EmbedBuilder embedBuilder = new EmbedBuilder();
        if (StringUtils.hasText(title)) {
            embedBuilder.setTitle(title);
        }
        for (String description : descriptions) {
            embedBuilder.appendDescription(description);
        }
        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }

    public void editMessage(String guildId, String messageId, String message) {
        MessageChannel channel = channelRepository.get(guildId);
        channel.editMessageById(messageId, message).queue();
    }
}
