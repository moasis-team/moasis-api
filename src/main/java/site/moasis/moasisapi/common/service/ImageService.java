package site.moasis.moasisapi.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Qualifier("imageWebClient")
    private final WebClient imageWebClient;

    @Value("${app.image-server-url}")
    private String imageScriptUrl;

    public String uploadFile(String encodedFileBase64) {
        return imageWebClient.post()
            .uri(imageScriptUrl)
            .body(BodyInserters.fromFormData("myFile", encodedFileBase64)
                .with("mimeType", "image/png")
                .with("fileName", "product.png"))
            .exchangeToMono(this::handleRedirect)
            .block();
    }

    private Mono<String> handleRedirect(ClientResponse response) {
        if (response.statusCode().is3xxRedirection()) {
            String location = Objects.requireNonNull(response.headers().asHttpHeaders().getLocation()).toString();
            return imageWebClient.get()
                .uri(location)
                .retrieve()
                .bodyToMono(String.class);
        } else {
            return response.bodyToMono(String.class);
        }
    }
}
