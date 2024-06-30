package site.moasis.moasisapi.client;

import reactor.core.publisher.Mono;

public interface ImageProxy {
    Mono<byte[]> getImage(String imageId);
}
