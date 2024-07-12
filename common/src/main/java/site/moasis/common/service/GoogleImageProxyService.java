package site.moasis.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import site.moasis.common.client.ImageProxy;


@Service
@RequiredArgsConstructor
public class GoogleImageProxyService implements ImageProxy {

    private final WebClient googleImageProxy;

    @Override
    public Mono<byte[]> getImage(String imageId) {
        return googleImageProxy.get()
            .uri("/download?id=" + imageId)
            .retrieve()
            .bodyToMono(byte[].class);
    }
}
