package wlsh.project.discordgames.catchmusic.domain;

import lombok.Getter;
import wlsh.project.discordgames.common.catchgames.domain.Round;

@Getter
public class CatchMusicRound extends Round {

    private Music music;

    public CatchMusicRound(int roundNumber, Music music) {
        super(roundNumber);
        this.music = music;
    }

    public static CatchMusicRound prototype(Music music) {
        return new CatchMusicRound(1, music);
    }

    @Override
    public String getAnswer() {
        String name = music.name()
                .toLowerCase();
        String regex = "\\([^()]*\\)";

        while (name.matches(".*\\([^()]*\\).*")) {
            name = name.replaceAll(regex, "").trim();
        }
        return name.trim();
    }

    @Override
    protected Round createWithPrototype(int roundNumber) {
        return new CatchMusicRound(roundNumber, music);
    }
}
