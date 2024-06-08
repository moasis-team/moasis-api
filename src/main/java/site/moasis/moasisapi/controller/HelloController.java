package site.moasis.moasisapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public ResponseEntity<CommonResponse<String>> hello() {
        return CommonResponse.success(null, "hello");
    }
}
