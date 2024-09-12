package wlsh.project.discordgames.common.catchgames.domain;

import lombok.Getter;

import java.util.Objects;

@Getter
public abstract class Round {

    protected int roundNumber;

    protected Answerer answerer;

    protected Answer answer;

    public Round(int roundNumber, Answer answer) {
        this.roundNumber = roundNumber;
        this.answer = answer;
    }

    protected abstract Round createWithPrototype(int roundNumber);

    public boolean answer(Player player) {
        if (Objects.nonNull(answerer)){
            System.out.println("이미 정답자가 존재합니다");
            return false;
        }
        Answer answer = getAnswer();
        if (answer.equals(player.answer())) {
            answerer = Answerer.of(player);
            return true;
        }
        return false;
    }

    public boolean isFinished() {
        return Objects.nonNull(answerer);
    }
}
