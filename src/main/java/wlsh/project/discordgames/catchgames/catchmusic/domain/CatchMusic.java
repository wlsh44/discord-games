package wlsh.project.discordgames.catchgames.catchmusic.domain;

import lombok.Getter;
import wlsh.project.discordgames.catchgames.catchmusic.infra.crawler.Radio;
import wlsh.project.discordgames.catchgames.common.catchgames.domain.CatchGame;
import wlsh.project.discordgames.catchgames.common.catchgames.domain.CatchGameId;

import java.util.List;

@Getter
public class CatchMusic extends CatchGame {

    private Radio radio;


    public CatchMusic(CatchGameId catchGameId, int currentRoundNumber, List<FilterOption> filterOptions, Radio radio, int finishScore) {
        super(catchGameId, currentRoundNumber, finishScore, filterOptions);
        this.radio = radio;
    }

    public static CatchMusic startGame(CatchGameId catchGameId, int finishScore, List<FilterOption> filterOptions, Radio radio) {
        return new CatchMusic(catchGameId, 0, filterOptions, radio, finishScore);
    }

    public CatchMusicRound getCurrentRound() {
        return (CatchMusicRound) rounds.get(currentRoundNumber - 1);
    }
//
//    public boolean answer(Player player) {
//        CatchMusicRound currentRound = getCurrentRound();
//
//        boolean result = currentRound.answer(player);
//        if (result) {
//            currentRoundNumber++;
//            Long answererScore = rounds.stream()
//                    .filter(CatchMusicRound::isFinished)
//                    .collect(Collectors.groupingBy(
//                            round -> round.getAnswerer().name(),
//                            Collectors.mapping(
//                                    CatchMusicRound::getAnswerer,
//                                    Collectors.counting())
//                    )).get(player.name());
//            if (answererScore == finishScore) {
//                this.status = Status.FINISHED;
//            }
//        }
//        return result;
//    }
//
//    public CatchMusicRound getCurrentRound() {
//        return rounds.get(currentRoundNumber - 1);
//    }
//
//    public boolean isFinished() {
//        return Status.FINISHED.equals(status);
//    }
//
//    public List<CatchMusicRound> getRounds() {
//        return Collections.unmodifiableList(rounds);
//    }
//
//    public void skipRound() {
//        this.currentRoundNumber++;
//    }
//
//    public void addRounds(List<Music> musicList) {
//        int roundNumber = currentRoundNumber;
//        for (Music music : musicList) {
//            CatchMusicRound round = new CatchMusicRound(roundNumber++, music);
//            rounds.add(round);
//            round.setCatchMusic(this);
//        }
//    }
//
//    public boolean isMoreRoundRequired() {
//        return currentRoundNumber >= rounds.get(rounds.size() - 1).getRoundNumber();
//    }
}
