package wlsh.project.discordgames.catchmusic.infra;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchgames.catchmusic.infra.spotify.ArtistInfo;
import wlsh.project.discordgames.catchgames.catchmusic.infra.spotify.MusicInfo;
import wlsh.project.discordgames.catchgames.catchmusic.infra.spotify.SpotifySearchService;

@SpringBootTest
class SpotifySearchServiceTest {

    @Autowired
    SpotifySearchService spotifySearchService;

    @Test
    @DisplayName("asda")
    void asda() throws Exception {
        //given


        //when
        ArtistInfo artistInfo = spotifySearchService.searchArtistInfo("NewJeans");

        //then
        System.out.println("artistInfo = " + artistInfo);
    }

    @Test
    @DisplayName("asd")
    void asd() throws Exception {
        //given


        //when
        MusicInfo musicInfo = spotifySearchService.searchMusicInfo(Music.of("사랑은...향기를 남기고", "테이"));

        //then
        System.out.println("musicInfo = " + musicInfo);
    }

    @Test
    @DisplayName("asd")
    void asd2() throws Exception {
        //given


        //when
        MusicInfo musicInfo = spotifySearchService.searchMusicInfo(Music.of("LOST STARS", "ADAM LEVINE"));

        //then
        System.out.println("musicInfo = " + musicInfo);
    }
}
