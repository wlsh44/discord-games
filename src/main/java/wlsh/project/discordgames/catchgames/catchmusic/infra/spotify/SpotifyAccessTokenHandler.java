package wlsh.project.discordgames.catchgames.catchmusic.infra.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Base64;

@Slf4j
@Component
public class SpotifyAccessTokenHandler {

    private final SpotifyConfig config;
    private final RestClient restClient;

    @Getter
    private String accessToken;

    public SpotifyAccessTokenHandler(
            SpotifyConfig config,
            @Qualifier("account") RestClient restClient
    ) {
        this.config = config;
        this.restClient = restClient;
        updateAccessToken();
    }

    public void updateAccessToken() {
        MultiValueMap<String, String> body = getBody();
        String authorization = getAuthorization();

        TokenResponse response = restClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/token")
                        .build())
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(body)
                .retrieve()
                .body(TokenResponse.class);
        log.info("accessToken = {}", response.accessToken);
        this.accessToken = response.accessToken();
    }

    private static MultiValueMap<String, String> getBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        return body;
    }

    public String getAuthorization() {
        return "Basic " + new String(Base64.getEncoder()
                .encode((config.getInfo().clientId() + ":" + config.getInfo().secret())
                        .getBytes()));
    }

    public record TokenResponse(
            @JsonProperty("access_token")
            String accessToken
    ) {
    }
}
