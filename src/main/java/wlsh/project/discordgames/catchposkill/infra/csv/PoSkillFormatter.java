package wlsh.project.discordgames.catchposkill.infra.csv;

import wlsh.project.discordgames.catchposkill.domain.PoSkill;
import wlsh.project.discordgames.common.infra.csv.CSVFormatter;

import java.util.ArrayList;
import java.util.List;

public class PoSkillFormatter implements CSVFormatter<PoSkill> {

    @Override
    public List<String[]> format(List<PoSkill> data) {
        return data.stream()
                .map(poSkill -> {
                    List<String> strings = new ArrayList<>();
                    strings.add(String.valueOf(poSkill.id()));
                    strings.add(poSkill.koName());
                    strings.add(poSkill.enName());
                    strings.add(poSkill.type());
                    strings.add(poSkill.attack());
                    strings.add(poSkill.description());
                    strings.add(poSkill.damage());
                    strings.add(poSkill.accuracy());
                    strings.add(String.valueOf(poSkill.pp()));
                    strings.add(poSkill.generation());
                    return strings.toArray(new String[0]);
                })
                .toList();
    }
}
