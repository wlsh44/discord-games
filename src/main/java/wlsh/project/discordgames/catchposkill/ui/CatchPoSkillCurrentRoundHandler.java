package wlsh.project.discordgames.catchposkill.ui;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchposkill.domain.CatchPoSkill;
import wlsh.project.discordgames.catchposkill.domain.CatchPoSkillRound;
import wlsh.project.discordgames.catchposkill.domain.PoSkill;
import wlsh.project.discordgames.catchposkill.infra.CatchPoSkillRepository;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.discord.util.DiscordMessageHandler;

@Service
@RequiredArgsConstructor
public class CatchPoSkillCurrentRoundHandler {

    private final CatchPoSkillRepository catchPoSkillRepository;
    private final DiscordMessageHandler messageHandler;

    public void show(CatchGameId catchGameId, MessageChannel messageChannel) {
        doCountdown(catchGameId.channelId());
        CatchPoSkill catchPoSkill = catchPoSkillRepository.findById(catchGameId)
                .orElseThrow(() -> new RuntimeException("없음"));
        CatchPoSkillRound currentRound = catchPoSkill.getCurrentRound();
        PoSkill poSkill = currentRound.getPoSkill();
        MessageEmbed messageEmbed = new EmbedBuilder()
                .setDescription("### 위력: `%s`\n".formatted(poSkill.damage()))
                .appendDescription("### 명중률: `%s`\n".formatted(poSkill.accuracy()))
                .appendDescription("### PP: `%s`\n".formatted(poSkill.pp()))
                .appendDescription("### 타입: `%s`\n".formatted(poSkill.type()))
                .appendDescription("### 분류: `%s`\n".formatted(poSkill.attack()))
                .appendDescription("### 세대: `%s`\n".formatted(poSkill.generation()))
                .appendDescription("### 설명\n```%s```".formatted(poSkill.description()))
                .build();
        messageChannel.sendMessageEmbeds(messageEmbed).queue();
    }

    private void doCountdown(String channelId) {
        try {
            int countdown = 5;
            messageHandler.sendMessage(channelId, "5초 뒤에 다음 라운드가 시작됩니다.");
            Thread.sleep(1000);
            String id = messageHandler.sendMessageWithId(channelId, String.valueOf(countdown--));
            while (countdown > 0) {
                Thread.sleep(1000);
                messageHandler.editMessage(channelId, id, String.valueOf(countdown--));
            }
            messageHandler.editMessage(channelId, id, "시작!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
