package wlsh.project.discordgames.pokemon.ui;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import wlsh.project.discordgames.discord.command.ICommand;
import wlsh.project.discordgames.pokemon.application.CatchPokemonCurrentRoundService;
import wlsh.project.discordgames.pokemon.application.NewCatchPokemonService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

@Component
@RequiredArgsConstructor
public class CatchPokemonNewGameDispatcher implements ICommand {

    private final NewCatchPokemonService newCatchPokemonService;
    private final CatchPokemonCurrentRoundService catchPokemonCurrentRoundService;

    @Override
    public String getName() {
        return "pokemon";
    }

    @Override
    public String getDescription() {
        return "pokemon";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.INTEGER, "finish-score", "게임 종료 점수", true).setRequiredRange(1, 10),
                new OptionData(OptionType.STRING, "exclude", "제외할 세대(,로 구분 ex 4,5,6)", false)
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
//        checkValidChannelState(event.getMember(), event.getGuild(), event.getChannel());
        if (!event.getChannel().getName().equals("캐치포켓몬")) {
            throw new RuntimeException("캐치포켓몬 명령어입니다.");
        }

        OptionMapping exclude = event.getOption("exclude");
        List<Integer> excludeList = List.of();
        if (Objects.nonNull(exclude)) {
            excludeList = Arrays.stream(exclude.getAsString().split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();
        }
        int round = requireNonNull(event.getOption("finish-score")).getAsInt();

//        newCatchMusicService.newCatchMusic(event.getGuild().getId(), Radio.valueOf(tag), round);
        newCatchPokemonService.newCatchPokemon(event.getGuild().getId(), excludeList, round);
        event.reply("게임이 시작됩니다.").queue();
        catchPokemonCurrentRoundService.show(event.getGuild().getId(), event.getChannel());
    }
}
