package site.moasis.moasisapi.common.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "imageClient")
    public WebClient imageClient(@Value("${app.image-api-url:}") String url) {
        return webClient(url);
    }

    @Bean
    @ConditionalOnMissingBean(name = "slackClient")
    public WebClient slackClient(@Value("${app.slack-alert-url:}") String url) {
        return webClient(url);
    }

    private WebClient webClient(String url) {
        return WebClient.builder()
            .baseUrl(url)
            .build();
    }
}
