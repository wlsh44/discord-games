package wlsh.project.discordgames.catchmusic.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CatchMusic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String guildId;

    @Enumerated(EnumType.STRING)
    private Status status;

    private int currentRoundNumber;

    private int finishScore;

    @OneToMany(mappedBy = "catchMusic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Round> rounds = new ArrayList<>();

    @Embedded
    private Tag tag;

    public CatchMusic(String guildId, List<Round> rounds, Tag tag, Status status, int finishScore) {
        this.guildId = guildId;
        this.rounds = rounds;
        this.tag = tag;
        this.status = status;
        this.currentRoundNumber = 1;
        this.finishScore = finishScore;
        rounds.forEach(round -> round.setCatchMusic(this));
    }

    public static CatchMusic startGame(String guildId, List<Round> rounds, Tag tag, int finishScore) {
        return new CatchMusic(guildId, rounds, tag, Status.PLAYING, finishScore);
    }

    public boolean answer(Player player) {
        Round currentRound = getCurrentRound();

        boolean result = currentRound.answer(player);
        if (result) {
            currentRoundNumber++;
            Long answererScore = rounds.stream()
                    .filter(Round::isFinished)
                    .collect(Collectors.groupingBy(
                            round -> round.getAnswerer().name(),
                            Collectors.mapping(
                                    Round::getAnswerer,
                                    Collectors.counting())
                    )).get(player.name());
            if (answererScore == finishScore) {
                this.status = Status.FINISHED;
            }
        }
        return result;
    }

    public Round getCurrentRound() {
        return rounds.get(currentRoundNumber - 1);
    }

    public boolean isFinished() {
        return Status.FINISHED.equals(status);
    }

    public List<Round> getRounds() {
        return Collections.unmodifiableList(rounds);
    }

    public void skipRound() {
        this.currentRoundNumber++;
    }

    public void addRounds(List<Music> musicList) {
        int roundNumber = currentRoundNumber;
        for (Music music : musicList) {
            Round round = new Round(roundNumber++, music);
            rounds.add(round);
            round.setCatchMusic(this);
        }
    }

    public boolean isMoreRoundRequired() {
        return currentRoundNumber >= rounds.get(rounds.size() - 1).getRoundNumber();
    }
}
