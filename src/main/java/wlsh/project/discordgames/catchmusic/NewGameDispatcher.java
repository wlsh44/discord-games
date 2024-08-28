package wlsh.project.discordgames.catchmusic;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchmusic.application.CreateCatchMusicService;
import wlsh.project.discordgames.catchmusic.application.PlayService;
import wlsh.project.discordgames.catchmusic.infra.crawler.Radio;
import wlsh.project.discordgames.discord.command.ICommand;

import java.util.Arrays;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static wlsh.project.discordgames.catchmusic.ChannelValidator.checkValidChannelState;

@Component
@RequiredArgsConstructor
public class NewGameDispatcher implements ICommand {

    private final CreateCatchMusicService newCatchMusicService;
    private final PlayService playService;

    @Override
    public String getName() {
        return "newgame";
    }

    @Override
    public String getDescription() {
        return "Create New Game";
    }

    @Override
    public List<OptionData> getOptions() {
        OptionData optionData = new OptionData(OptionType.STRING, "tag", "태그", true);
        Arrays.stream(Radio.values())
                .forEach(radio -> optionData.addChoice(radio.getRadioName(), radio.name()));
        return List.of(
                optionData,
                new OptionData(OptionType.INTEGER, "finish-score", "게임 종료 점수", true).setRequiredRange(1, 10)
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        checkValidChannelState(event.getMember(), event.getGuild(), event.getChannel());

        String tag = requireNonNull(event.getOption("tag")).getAsString();
        int round = requireNonNull(event.getOption("finish-score")).getAsInt();

        newCatchMusicService.newCatchMusic(event.getGuild().getId(), Radio.valueOf(tag), round);
        event.reply("게임이 시작됩니다.").queue();
    }
}
