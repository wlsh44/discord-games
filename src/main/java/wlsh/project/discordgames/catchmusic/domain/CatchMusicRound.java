package wlsh.project.discordgames.catchmusic.domain;

import lombok.Getter;
import wlsh.project.discordgames.common.catchgames.domain.Answer;
import wlsh.project.discordgames.common.catchgames.domain.Round;

import java.util.Objects;
import java.util.Optional;

@Getter
public class CatchMusicRound extends Round {

    private Music music;

    public CatchMusicRound(int roundNumber, Music music) {
        super(roundNumber, makeAnswer(music));
        this.music = music;
    }

    public static CatchMusicRound prototype(Music music) {
        return new CatchMusicRound(1, music);
    }

    public static Answer makeAnswer(Music music) {
        String name = music.name()
                .toLowerCase();
        String regex = "\\([^()]*\\)";

        while (name.matches(".*\\([^()]*\\).*")) {
            name = name.replaceAll(regex, "").trim();
        }
        String second = "";
        if (Objects.nonNull(music.secondName())) {
            second = music.secondName().trim();
        }
        return new Answer(name.trim(), second);
    }

    @Override
    protected Round createWithPrototype(int roundNumber) {
        return new CatchMusicRound(roundNumber, music);
    }
}
