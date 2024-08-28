package wlsh.project.discordgames.discord;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ChannelRepository {

    private final Map<String, MessageChannel> data = new HashMap<>();

    public void save(String guildId, MessageChannel messageChannel) {
        data.put(guildId, messageChannel);
    }

    public MessageChannel get(String guildId) {
        return data.get(guildId);
    }
}
