package wlsh.project.discordgames.discord;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageEventListener extends ListenerAdapter {

    private final GuildRepository guildRepository;
    private final ChannelRepository channelRepository;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        TextChannel textChannel = event.getChannel().asTextChannel();
        Message message = event.getMessage();

        log.info("author: {}", author);
        log.info("textChannel: {}", textChannel);
        log.info("get message: {}", message.getContentDisplay());

        Guild guild = event.getGuild();
        guildRepository.save(guild.getId(), guild);
        channelRepository.save(event.getChannel().getId(), message.getChannel());
    }
}
