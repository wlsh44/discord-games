package wlsh.project.discordgames.catchposkill.ui;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchposkill.application.NewCatchPoSkillUseCase;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.discord.ChannelRepository;
import wlsh.project.discordgames.discord.command.ICommand;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Component
@RequiredArgsConstructor
public class CatchPoSkillNewGameDispatcher implements ICommand {

    private final NewCatchPoSkillUseCase newCatchPoSkillUseCase;
    private final CatchPoSkillCurrentRoundHandler catchPoSkillCurrentRoundHandler;
    private final ChannelRepository channelRepository;

    @Override
    public String getName() {
        return "poskill";
    }

    @Override
    public String getDescription() {
        return "poskill";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.INTEGER, "finish-score", "게임 종료 점수", true).setRequiredRange(1, 10)
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (!event.getChannel().getName().equals("캐치포스킬")) {
            throw new RuntimeException("캐치포스킬 명령어입니다.");
        }
        channelRepository.save(event.getChannelId(), event.getChannel());

        int round = requireNonNull(event.getOption("finish-score")).getAsInt();

        CatchGameId catchGameId = new CatchGameId(event.getGuild().getId(), event.getChannelId());
        newCatchPoSkillUseCase.newCatchPoSkill(catchGameId, round);
        event.reply("게임이 시작됩니다.").queue();
        catchPoSkillCurrentRoundHandler.show(catchGameId, event.getChannel());
    }
}
