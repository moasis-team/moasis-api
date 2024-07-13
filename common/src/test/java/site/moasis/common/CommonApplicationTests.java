package site.moasis.common;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaAuditing
@Configuration
@EnableJpaRepositories(basePackages = {"site.moasis.common.repository"})
@EntityScan(basePackages = {"site.moasis.common.entity"})
class CommonApplicationTests {

    @Test
    void contextLoads() {
    }

}
