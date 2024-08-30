package wlsh.project.discordgames.pokemon.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Round2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private CatchPokemon catchPokemon;

    private int roundNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "answerer_id")),
            @AttributeOverride(name = "name", column = @Column(name = "answerer_name"))
    })
    private Answerer answerer;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "music_name")),
    })
    private Pokemon pokemon;

    public Round2(int roundNumber, Pokemon pokemon) {
        this.roundNumber = roundNumber;
        this.pokemon = pokemon;
    }

    public Round2(int roundNumber, Answerer answerer, Pokemon pokemon) {
        this.roundNumber = roundNumber;
        this.answerer = answerer;
        this.pokemon = pokemon;
    }

    public boolean isNotFinished() {
        return Objects.isNull(answerer);
    }

    public boolean answer(Player player) {
        if (Objects.nonNull(answerer)){
            System.out.println("이미 정답자가 존재합니다");
            return false;
        }
        String koAnswer = getKoAnswer();
        String enAnswer = getEnAnswer();
        String playerAnswer = player.answer().toLowerCase().trim();
        if (koAnswer.equals(playerAnswer) || enAnswer.equals(playerAnswer)) {
            answerer = Answerer.of(player);
            return true;
        }
        return false;
    }

    public String getKoAnswer() {
        String name = pokemon.getKo()
                .toLowerCase();
        String regex = "\\([^()]*\\)"; // 이 정규식은 중첩되지 않은 괄호만 제거합니다.

        // 중첩된 괄호도 처리하기 위해 루프를 사용하여 반복적으로 제거
        while (name.matches(".*\\([^()]*\\).*")) {
            name = name.replaceAll(regex, "").trim();
        }
        return name.trim();
    }

    public String getEnAnswer() {
        String name = pokemon.getEn()
                .toLowerCase();
        return name.trim();
    }

    public void setCatchPokemon(CatchPokemon catchPokemon) {
        this.catchPokemon = catchPokemon;
    }

    public boolean isFinished() {
        return Objects.nonNull(answerer);
    }
}
