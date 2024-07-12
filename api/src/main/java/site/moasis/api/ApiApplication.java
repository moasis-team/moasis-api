package site.moasis.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@ComponentScan(basePackages = {"site.moasis.api", "site.moasis.common"})
@EnableJpaRepositories(basePackages = {"site.moasis.common.repository"})
@EntityScan(basePackages = {"site.moasis.common.entity"})
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ApiApplication.class);
        Map<String, Object> defaultProperties = new HashMap<>();
        defaultProperties.put("spring.profiles.default", "local");
        app.setDefaultProperties(defaultProperties);
        app.run(args);
    }
}
