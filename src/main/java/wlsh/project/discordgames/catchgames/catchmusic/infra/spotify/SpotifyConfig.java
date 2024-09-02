package wlsh.project.discordgames.catchgames.catchmusic.infra.spotify;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Getter
@Configuration
public class SpotifyConfig {

    private static final String SPOTIFY_ACCOUNT_URL = "https://accounts.spotify.com";
    private static final String SPOTIFY_API_URL = "https://api.spotify.com";

    private final SpotifyInfo info;

    public SpotifyConfig(
            @Value("${spotify.clientId}") String clientId,
            @Value("${spotify.secret}") String secret
    ) {
        this.info = new SpotifyInfo(clientId, secret);
    }

    @Bean
    @Qualifier("account")
    public RestClient accountRestClient() {
        return RestClient.builder()
                .baseUrl(SPOTIFY_ACCOUNT_URL)
                .build();
    }

    @Bean
    @Qualifier("api")
    public RestClient apiRestClient() {
        return RestClient.builder()
                .baseUrl(SPOTIFY_API_URL)
                .build();
    }

    record SpotifyInfo(
            String clientId,
            String secret
    ) {
    }
}
