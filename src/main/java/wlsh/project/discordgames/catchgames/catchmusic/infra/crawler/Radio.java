package wlsh.project.discordgames.catchgames.catchmusic.infra.crawler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Radio {
    MUSIC_PARTY("정오의 희망곡 김신영입니다", "FM4U000001226", 3000, 5544),
    BRUNCH_CAFE("이석훈의 브런치카페", "FM4U000001334", 1, 876),
    STARNIGHT("김이나의 별이 빛나는 밤에", "RASFM210", 2000, 6435),
    MUSIC_CAMP("배철수의 음악캠프", "RAMFM300", 3000, 6712);

    private final String radioName;
    private final String progCode;
    private final int minSeqId;
    private final int maxSeqId;
}
