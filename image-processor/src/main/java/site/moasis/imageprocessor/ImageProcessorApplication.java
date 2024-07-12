package site.moasis.imageprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@ComponentScan(basePackages = {"site.moasis.imageprocessor", "site.moasis.common"})
public class ImageProcessorApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ImageProcessorApplication.class);
        Map<String, Object> defaultProperties = new HashMap<>();
        defaultProperties.put("spring.profiles.default", "local");
        app.setDefaultProperties(defaultProperties);
        app.run(args);
    }
}
