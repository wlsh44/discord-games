package wlsh.project.discordgames.catchposkill.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchpokemon.domain.CatchPokemon;
import wlsh.project.discordgames.catchpokemon.domain.Pokemon;
import wlsh.project.discordgames.catchpokemon.infra.csv.PokemonParser;
import wlsh.project.discordgames.catchposkill.domain.CatchPoSkill;
import wlsh.project.discordgames.catchposkill.domain.PoSkill;
import wlsh.project.discordgames.catchposkill.infra.csv.PoSkillParser;
import wlsh.project.discordgames.common.infra.csv.CSVService;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class PoSkillLoader {

    private final CSVService csvService;

    public PoSkill loadPokemon(CatchPoSkill catchPoSkill) {
        List<PoSkill> poskills = csvService.readData("po-skill.csv", new PoSkillParser());
        Random random = new Random();

        return Stream.generate(() -> random.nextInt(poskills.size()))
                .map(poskills::get)
//                .filter(randomPokemon -> catchPokemon.getPokemonFilterOptions()
//                        .stream()
//                        .allMatch(filterOption -> filterOption.doFilter(randomPokemon)))
                .limit(1)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("없음"));
    }
}
