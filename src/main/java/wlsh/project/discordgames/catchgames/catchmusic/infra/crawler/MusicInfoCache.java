package wlsh.project.discordgames.catchgames.catchmusic.infra.crawler;

import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchgames.catchmusic.domain.CatchMusic;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchgames.catchmusic.infra.spotify.MusicInfo;
import wlsh.project.discordgames.catchgames.common.catchgames.domain.CatchGameId;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;

@Component
public class MusicInfoCache {

    private final Queue<Music> data = new PriorityQueue<>((o1, o2) -> {
        if (o1.popularity() == o2.popularity()) {
            return 0;
        } else {
            return o1.popularity() - o2.popularity();
        }
    });

    public void save(Music music) {
        data.add(music);
    }

    public Music get() {
        return data.poll();
    }

    public int size() {
        return data.size();
    }

    public void clear() {
        data.clear();
    }
}
