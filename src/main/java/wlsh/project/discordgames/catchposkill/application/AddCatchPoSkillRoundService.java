package wlsh.project.discordgames.catchposkill.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wlsh.project.discordgames.catchpokemon.domain.CatchPokemon;
import wlsh.project.discordgames.catchpokemon.domain.CatchPokemonRound;
import wlsh.project.discordgames.catchpokemon.domain.Pokemon;
import wlsh.project.discordgames.catchpokemon.infra.PokemonLoader;
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
