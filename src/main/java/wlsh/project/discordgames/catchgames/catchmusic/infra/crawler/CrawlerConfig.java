package wlsh.project.discordgames.catchgames.catchmusic.infra.crawler;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class CrawlerConfig {

    public static final String MBC_FM4U_URL = "https://miniweb.imbc.com/Music/View";

    @Bean
    @Qualifier("mbcFM4U")
    public RestClient mbcFM4URestClient() {
        return RestClient.builder()
                .baseUrl(MBC_FM4U_URL + "?seqID=5544&progCode=FM4U000001226&page=1")
                .build();
    }
}
