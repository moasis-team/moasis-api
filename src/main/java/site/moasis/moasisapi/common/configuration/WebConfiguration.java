package site.moasis.moasisapi.common.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.moasis.moasisapi.common.interceptor.CommonHttpRequestInterceptor;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration {

    @Value("${cors.allowed-origin-url:}")
    private String originUrl;

    private final CommonHttpRequestInterceptor interceptor;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                    .addMapping("/api/**")
                    .allowedOrigins(originUrl)
                    .allowedHeaders("*")
                    .allowedMethods("GET", "POST", "PATCH", "DELETE");
            }
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(interceptor);
            }
        };
    }
}
