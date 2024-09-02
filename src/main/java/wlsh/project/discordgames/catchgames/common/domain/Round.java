package wlsh.project.discordgames.catchgames.common.domain;

import lombok.Getter;
import wlsh.project.discordgames.catchmusic.domain.CatchMusic;

import java.util.Objects;

@Getter
public abstract class Round {

    protected int roundNumber;

    protected Answerer answerer;

    public Round(int roundNumber, Answerer answerer) {
        this.roundNumber = roundNumber;
        this.answerer = answerer;
    }

    public Round(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    protected abstract String getAnswer();

    protected abstract Round createWithPrototype(int roundNumber);

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

//    public String getAnswer() {
//        String name = "music"
//                .toLowerCase();
//        String regex = "\\([^()]*\\)"; // 이 정규식은 중첩되지 않은 괄호만 제거합니다.
//
//        // 중첩된 괄호도 처리하기 위해 루프를 사용하여 반복적으로 제거
//        while (name.matches(".*\\([^()]*\\).*")) {
//            name = name.replaceAll(regex, "").trim();
//        }
//        return name.trim();
//    }

    public boolean isFinished() {
        return Objects.nonNull(answerer);
    }
}
