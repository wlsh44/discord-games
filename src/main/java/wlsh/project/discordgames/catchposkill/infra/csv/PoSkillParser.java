package wlsh.project.discordgames.catchposkill.infra.csv;

import wlsh.project.discordgames.catchposkill.domain.PoSkill;
import wlsh.project.discordgames.common.infra.csv.CSVParser;

public class PoSkillParser implements CSVParser<PoSkill> {

    @Override
    public PoSkill parse(String[] poSkill) {
        String id = poSkill[0];
        String ko = poSkill[1];
        String en = poSkill[2];
        String type = poSkill[3];
        String attack = poSkill[4];
        String description = poSkill[5];
        String damage = poSkill[6];
        String accuracy = poSkill[7];
        String pp = poSkill[8];
        String generation = poSkill[9];
        return new PoSkill(id, ko, en, type, attack, description, damage, accuracy, pp, generation);
    }
}
