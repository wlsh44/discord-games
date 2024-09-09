package wlsh.project.discordgames.catchmusic.infra.crawler;

import org.springframework.stereotype.Component;
import wlsh.project.discordgames.catchmusic.domain.Music;

import java.util.PriorityQueue;
import java.util.Queue;

@Component
public class MusicInfoCache {

    private final Queue<Music> data = new PriorityQueue<>((o1, o2) -> {
        if (o1.popularity() == o2.popularity()) {
            return 0;
        } else {
            return o2.popularity() - o1.popularity();
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
