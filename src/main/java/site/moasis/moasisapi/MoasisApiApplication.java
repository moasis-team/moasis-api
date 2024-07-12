package site.moasis.moasisapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class MoasisApiApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MoasisApiApplication.class);
        Map<String, Object> defaultProperties = new HashMap<>();
        defaultProperties.put("spring.profiles.default", "local");
        app.setDefaultProperties(defaultProperties);
        app.run(args);
    }
}
