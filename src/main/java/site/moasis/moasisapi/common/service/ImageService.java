package site.moasis.moasisapi.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import site.moasis.moasisapi.client.ImageClient;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageService implements ImageClient {

    @Qualifier("imageClient")
    private final WebClient imageClient;

    @Value("${app.image-creation-path}")
    private String imageCreationPath;

    @Value("${app.image-deletion-path}")
    private String imageDeletionPath;

    @Override
    public String uploadFile(String encodedFileBase64) {
        return imageClient.post()
            .uri(imageCreationPath)
            .body(BodyInserters.fromFormData("myFile", encodedFileBase64)
                .with("mimeType", "image/png")
                .with("fileName", "product.png"))
            .exchangeToMono(this::handleRedirect)
            .block();
    }

    @Override
    public boolean deleteFile(String imageUrl) {
        String imageId = imageUrl.split("id=")[1];
        String requestUri = UriComponentsBuilder.fromUriString(imageDeletionPath)
            .query("fileId={imageId}")
            .buildAndExpand(imageId)
            .toUriString();
        String responseMessage = imageClient.post()
            .uri(requestUri)
            .exchangeToMono(this::handleRedirect)
            .block();
        return responseMessage != null;
    }

    private Mono<String> handleRedirect(ClientResponse response) {
        if (response.statusCode().is3xxRedirection()) {
            String location = Objects.requireNonNull(response.headers().asHttpHeaders().getLocation()).toString();
            return imageClient.get()
                .uri(location)
                .retrieve()
                .bodyToMono(String.class);
        } else {
            return response.bodyToMono(String.class);
        }
    }
}
