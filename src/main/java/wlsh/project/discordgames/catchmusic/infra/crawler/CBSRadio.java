package wlsh.project.discordgames.catchmusic.infra.crawler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@RequiredArgsConstructor
public enum CBSRadio implements Radio {
    MORNING("김용신의 그대와 여는 아침", "cbs_P000218", LocalDate.of(2019, 9, 8), LocalDate.of(2024, 9, 8));

    private final String radioName;
    private final String programCode;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public String getRandom() {
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);
        return LocalDate.ofEpochDay(randomDay).toString();
    }
}
