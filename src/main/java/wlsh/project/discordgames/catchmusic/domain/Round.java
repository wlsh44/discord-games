package wlsh.project.discordgames.catchmusic.domain;

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
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private CatchMusic catchMusic;

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
    private Music music;

    public Round(int roundNumber, Music music) {
        this.roundNumber = roundNumber;
        this.music = music;
    }

    public Round(int roundNumber, Answerer answerer, Music music) {
        this.roundNumber = roundNumber;
        this.answerer = answerer;
        this.music = music;
    }

    public boolean isNotFinished() {
        return Objects.isNull(answerer);
    }

    public boolean answer(Player player) {
        if (Objects.nonNull(answerer)){
            System.out.println("이미 정답자가 존재합니다");
            return false;
        }
        String answer = getAnswer();
        if (answer.equals(player.answer().toLowerCase().trim())) {
            answerer = Answerer.of(player);
            return true;
        }
        return false;
    }

    public String getAnswer() {
        String name = music.name()
                .toLowerCase();
        String regex = "\\([^()]*\\)"; // 이 정규식은 중첩되지 않은 괄호만 제거합니다.

        // 중첩된 괄호도 처리하기 위해 루프를 사용하여 반복적으로 제거
        while (name.matches(".*\\([^()]*\\).*")) {
            name = name.replaceAll(regex, "").trim();
        }
        return name.trim();
    }

    public void setCatchMusic(CatchMusic catchMusic) {
        this.catchMusic = catchMusic;
    }

    public boolean isFinished() {
        return Objects.nonNull(answerer);
    }
}
