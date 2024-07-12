package site.moasis.common.client;

import reactor.core.publisher.Mono;

public interface ImageProxy {
    Mono<byte[]> getImage(String imageId);
}
