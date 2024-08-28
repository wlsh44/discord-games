package wlsh.project.discordgames.discord;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class GuildRepository {

    private final Map<String, Guild> data = new HashMap<>();

    public void save(String guildId, Guild guild) {
        data.put(guildId, guild);
    }

    public Guild get(String guildId) {
        return data.get(guildId);
    }
}
