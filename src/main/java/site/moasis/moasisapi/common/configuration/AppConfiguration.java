package site.moasis.moasisapi.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfiguration {

    @Bean("imageWebClient")
    public WebClient imageWebClient() {
        return WebClient.builder()
            .baseUrl("https://script.google.com/macros/s")
            .build();
    }
}