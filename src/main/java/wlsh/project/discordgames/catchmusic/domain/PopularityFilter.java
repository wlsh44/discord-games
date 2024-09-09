package wlsh.project.discordgames.catchmusic.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PopularityFilter implements FilterOption {

    private final int popularity;

    @Override
    public boolean doFilter(Music music) {
        return this.popularity <= music.popularity();
    }
}
