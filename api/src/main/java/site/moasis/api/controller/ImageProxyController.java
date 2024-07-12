package site.moasis.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import site.moasis.common.client.ImageProxy;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/image-proxy")
@RequiredArgsConstructor
public class ImageProxyController {

    private final ImageProxy imageProxy;

    @GetMapping()
    public Mono<ResponseEntity<byte[]>> fetchImage(@RequestParam String id) {

        return imageProxy.getImage(id)
            .map(imageBytes -> {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_PNG);
                headers.setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS).cachePublic().getHeaderValue());

                return ResponseEntity.ok().headers(headers).body(imageBytes);
            });
    }
}
