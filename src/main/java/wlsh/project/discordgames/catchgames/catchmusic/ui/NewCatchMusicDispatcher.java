package wlsh.project.discordgames.catchgames.catchmusic.ui;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchgames.catchmusic.application.NewCatchMusicUseCase;
import wlsh.project.discordgames.catchgames.catchmusic.application.PlayMusicEvent;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusicRound;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchgames.catchmusic.infra.crawler.Radio;
import wlsh.project.discordgames.catchgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.discord.command.ICommand;

import java.util.Arrays;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static wlsh.project.discordgames.catchgames.catchmusic.ui.ChannelValidator.checkValidChannelState;

@Component
@RequiredArgsConstructor
public class NewCatchMusicDispatcher implements ICommand {

    private final NewCatchMusicUseCase newCatchMusicUseCase;
    private final MusicPlayerHandler musicPlayerHandler;

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
        int finalScore = requireNonNull(event.getOption("finish-score")).getAsInt();

        CatchGameId catchGameId = new CatchGameId(event.getGuild().getId(), event.getChannel().getId());
        CatchMusic catchMusic = newCatchMusicUseCase.newCatchMusic(catchGameId, Radio.valueOf(tag), finalScore);
        event.reply("게임이 시작됩니다.").queue();

        CatchMusicRound round = catchMusic.getCurrentRound();
        Music music = round.getMusic();
        musicPlayerHandler.playMusic(new PlayMusicEvent(catchGameId, music));
    }
}
