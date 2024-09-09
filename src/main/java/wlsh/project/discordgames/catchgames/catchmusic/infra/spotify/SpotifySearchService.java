package wlsh.project.discordgames.catchgames.catchmusic.infra.spotify;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import wlsh.project.discordgames.catchgames.catchmusic.domain.Music;
import wlsh.project.discordgames.catchgames.catchmusic.infra.spotify.exception.SpotifyException;
import wlsh.project.discordgames.catchgames.catchmusic.infra.spotify.exception.SpotifyTokenExpiredException;
import wlsh.project.discordgames.catchgames.catchmusic.infra.spotify.exception.SpotifyTooManyRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SpotifySearchService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final SpotifyAccessTokenHandler tokenHandler;

    public SpotifySearchService(
            @Qualifier("api") RestClient restClient,
            ObjectMapper objectMapper,
            SpotifyAccessTokenHandler tokenHandler) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
        this.tokenHandler = tokenHandler;
    }

    public ArtistInfo searchArtistInfo(String artistName) {
        ArtistInfoResponse res = restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v1/search")
                        .queryParam("q", artistName)
                        .queryParam("market", "KR")
                        .queryParam("type", "artist")
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(tokenHandler.getAccessToken()))
                .header(HttpHeaders.ACCEPT_LANGUAGE, "ko")
                .retrieve()
                .onStatus(status -> status.value() != HttpStatus.OK.value(),
                        (request, response) -> handleError(artistName, response))
                .body(ArtistInfoResponse.class);
        ArtistInfoResponse.Artist artist = res.artists().items().get(0);
        return new ArtistInfo(artistName, artist.id(), artist.images().get(0).url());
    }

    public MusicInfo searchMusicInfo(Music music) {
        ArtistInfo artistInfo = searchArtistInfo(music.artist().name());
        MusicSearchResponse res = restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v1/search")
                        .queryParam("q", music.name())
                        .queryParam("market", "KR")
                        .queryParam("type", "track")
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(tokenHandler.getAccessToken()))
                .header(HttpHeaders.ACCEPT_LANGUAGE, "ko")
                .retrieve()
                .onStatus(status -> status.value() != HttpStatus.OK.value(),
                        (request, response) -> handleError(music.name(), response))
                .body(MusicSearchResponse.class);
        List<MusicSearchResponse.Item> items = res.tracks().items();
        if (items.isEmpty()) {
            throw new RuntimeException("앨범 정보를 찾지 못했습니다.");
        }
        MusicSearchResponse.Item mostRelevantItem = items.stream()
                .filter(item -> item.album().artists().get(0).id().equals(artistInfo.spotifyId()))
                .findFirst()
                .orElseGet(() -> {
                    String name = items.get(0).album().artists().get(0).name().toLowerCase();
                    String name2 = music.artist().name().toLowerCase();
                    if (name.equals(name2)) {
                        return items.get(0);
                    }
                    throw new RuntimeException("앨범 정보를 찾지 못했습니다.");
                });
        MusicSearchResponse.Album album = mostRelevantItem.album();
        String albumImage = album
                .images()
                .get(0).url();
        return new MusicInfo(mostRelevantItem.name(), artistInfo.artistName(), artistInfo.url(), albumImage, album.name(), album.releaseDate(), mostRelevantItem.popularity());
    }

    private void handleError(String data, ClientHttpResponse response) throws IOException {
        String str = getErrorMessage(response);
        try {
            SpotifyErrorResponse res = objectMapper.readValue(str, SpotifyErrorResponse.class);

            String message = res.error().message();
            int status = res.error().status();
            switch (message) {
                case SpotifyTokenExpiredException.MESSAGE -> throw new SpotifyTokenExpiredException(data);
                case SpotifyTooManyRequestException.MESSAGE -> throw new SpotifyTooManyRequestException(data, getRetryAfter(response));
                default -> throw new SpotifyException(status, message);
            }
        } catch (Exception e) {
            if (response.getStatusCode().value() == 429) {
                throw new SpotifyTooManyRequestException(data, getRetryAfter(response));
            }
        }
    }

    private int getRetryAfter(ClientHttpResponse response) {
        return Optional.ofNullable(response.getHeaders().get("retry-after"))
                .map(header -> Integer.parseInt(header.get(0)))
                .orElse(3000);
    }

    private String getErrorMessage(ClientHttpResponse response) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            log.error(e.getMessage());
            return "";
        }
    }
}
