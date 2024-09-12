package wlsh.project.discordgames.catchmusic.domain;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class YearFilter implements FilterOption {

    private final int year;

    @Override
    public boolean doFilter(Music music) {
        try {
            String releaseDate = music.releaseDate();
            int year = LocalDate.parse(releaseDate, DateTimeFormatter.ISO_DATE).getYear();
            return this.year <= year;
        } catch (Exception e) {
            return false;
        }
    }
}
