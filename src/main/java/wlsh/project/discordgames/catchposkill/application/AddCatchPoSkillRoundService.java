package wlsh.project.discordgames.catchposkill.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchposkill.domain.CatchPoSkill;
import wlsh.project.discordgames.catchposkill.domain.CatchPoSkillRound;
import wlsh.project.discordgames.catchposkill.domain.PoSkill;
import wlsh.project.discordgames.catchposkill.infra.PoSkillLoader;

@Service
@RequiredArgsConstructor
public class AddCatchPoSkillRoundService {

    private final PoSkillLoader pokemonLoader;

    public void addRound(CatchPoSkill catchPoSkill) {
        PoSkill poSkill = pokemonLoader.loadPokemon(catchPoSkill);
        catchPoSkill.updateNewRound(CatchPoSkillRound.prototype(poSkill));
    }
}
