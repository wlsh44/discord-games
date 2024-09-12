package wlsh.project.discordgames.catchposkill.domain;

import lombok.Getter;
import wlsh.project.discordgames.common.catchgames.domain.Answer;
import wlsh.project.discordgames.common.catchgames.domain.Round;

@Getter
public class CatchPoSkillRound extends Round {

    private PoSkill poSkill;

    public CatchPoSkillRound(int roundNumber, PoSkill poSkill) {
        super(roundNumber, makeAnswer(poSkill));
        this.poSkill = poSkill;
    }

    public static CatchPoSkillRound prototype(PoSkill poSkill) {
        return new CatchPoSkillRound(1, poSkill);
    }

    @Override
    protected Round createWithPrototype(int roundNumber) {
        return new CatchPoSkillRound(roundNumber, poSkill);
    }

    public static Answer makeAnswer(PoSkill poSkill) {
        String name = poSkill.koName()
                .toLowerCase();
        String regex = "\\([^()]*\\)";

        while (name.matches(".*\\([^()]*\\).*")) {
            name = name.replaceAll(regex, "").trim();
        }
        String koAnswer = name.trim();
        String enAnswer = poSkill.enName()
                .toLowerCase().trim();
        return new Answer(koAnswer, enAnswer);
    }
}
