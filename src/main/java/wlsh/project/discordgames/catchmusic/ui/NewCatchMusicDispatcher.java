package wlsh.project.discordgames.catchmusic.ui;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchmusic.application.NewCatchMusicUseCase;
import wlsh.project.discordgames.catchmusic.application.PlayMusicEvent;
import wlsh.project.discordgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchmusic.domain.CatchMusicRound;
import wlsh.project.discordgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchmusic.infra.crawler.CBSRadio;
import wlsh.project.discordgames.catchmusic.infra.crawler.MBCRadio;
import wlsh.project.discordgames.common.catchgames.domain.CatchGameId;
import wlsh.project.discordgames.catchmusic.infra.crawler.Radio;
import wlsh.project.discordgames.discord.command.ICommand;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

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
        Arrays.stream(MBCRadio.values())
                .forEach(radio -> optionData.addChoice(radio.getRadioName(), radio.name()));
        Arrays.stream(CBSRadio.values())
                .forEach(radio -> optionData.addChoice(radio.getRadioName(), radio.name()));
        return List.of(
                optionData,
                new OptionData(OptionType.INTEGER, "finish-score", "게임 종료 점수", true).setRequiredRange(1, 10),
                new OptionData(OptionType.INTEGER, "popularity", "유명도 (기본 값 20)", false).setRequiredRange(20, 80),
                new OptionData(OptionType.INTEGER, "year", "이후", false).setMaxValue(2020)
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        ChannelValidator.checkValidChannelState(event.getMember(), event.getGuild(), event.getChannel());

        String tag = requireNonNull(event.getOption("tag")).getAsString();
        int finalScore = requireNonNull(event.getOption("finish-score")).getAsInt();
        int popularity = Optional.ofNullable(event.getOption("popularity"))
                .map(OptionMapping::getAsInt)
                .orElse(20);
        int year = Optional.ofNullable(event.getOption("year"))
                .map(OptionMapping::getAsInt)
                .orElse(1900);

        CatchGameId catchGameId = new CatchGameId(event.getGuild().getId(), event.getChannel().getId());
        CatchMusic catchMusic = newCatchMusicUseCase.newCatchMusic(catchGameId, getRadio(tag), finalScore, popularity, year);
        event.reply("게임이 시작됩니다.").queue();

        CatchMusicRound round = catchMusic.getCurrentRound();
        Music music = round.getMusic();
        musicPlayerHandler.playMusic(new PlayMusicEvent(catchGameId, music));
    }

    public Radio getRadio(String tag) {
        try {
            return CBSRadio.valueOf(tag);
        } catch (IllegalArgumentException e) {
            try {
                return MBCRadio.valueOf(tag);
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("No enum constant found for tag: " + tag);
            }
        }
    }
}
