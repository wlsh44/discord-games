package wlsh.project.discordgames.catchmusic.ui;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import static java.util.Objects.requireNonNull;

public class ChannelValidator {

    public static void checkValidChannelState(Member member, Guild guild, Channel channel) {
        requireNonNull(member);
        GuildVoiceState memberVoiceState = requireNonNull(member.getVoiceState());
        checkInAudioChannel(memberVoiceState);

        Member bot = requireNonNull(guild).getSelfMember();
        GuildVoiceState botVoiceState = requireNonNull(bot.getVoiceState());
        checkBotInAudioChannel(botVoiceState);
        checkIsCatchMusicTextChannel(channel, requireNonNull(botVoiceState.getChannel()));
    }

    public static void checkInAudioChannel(GuildVoiceState memberVoiceState) {
        if (!memberVoiceState.inAudioChannel()) {
//            throw new RuntimeException("음성 채널에 들어와있어야 합니다.");
        }
    }

    public static void checkIsCatchMusicTextChannel(Channel textChannel, Channel botChannel) {
        if (!botChannel.getName().equals(textChannel.getName())) {
//            throw new RuntimeException("봇과 같은 이름의 채팅 채널에서 채팅해주세요.");
        }
    }

    public static void checkBotInAudioChannel(GuildVoiceState botVoiceState) {
        if (!botVoiceState.inAudioChannel()) {
//            throw new RuntimeException("게임을 시작해주세요.");
        }
    }
}
