package site.moasis.moasisapi.common.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import static site.moasis.moasisapi.common.Json.objectMapper;

@Configuration
public class AppConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "imageClient")
    public WebClient imageClient(@Value("${app.image-api-url:}") String url) {
        return webClient(url, objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(name = "slackClient")
    public WebClient slackClient(@Value("${app.slack-alert-url:}") String url) {
        return webClient(url, objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(name = "googleImageProxy")
    public WebClient googleImageProxy(@Value("${app.google-drive-url:}") String url) {
        return webClient(url, objectMapper);
    }


    private WebClient webClient(String baseURL, ObjectMapper mapper) {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(clientCodecConfigurer -> {
                clientCodecConfigurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(mapper));
                clientCodecConfigurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(mapper));
            })
            .build();
        return WebClient.builder()
            .baseUrl(baseURL)
            .clientConnector(new ReactorClientHttpConnector(HttpClient.create(baseConnectionProvider())))
            .exchangeStrategies(strategies)
            .build();
    }

    private ConnectionProvider baseConnectionProvider() {
        return ConnectionProvider.builder("fixed")
            .maxConnections(50)
            .maxIdleTime(java.time.Duration.ofSeconds(20))
            .maxLifeTime(java.time.Duration.ofSeconds(60))
            .pendingAcquireTimeout(java.time.Duration.ofSeconds(60))
            .evictInBackground(java.time.Duration.ofSeconds(120)).build();
    }
}
