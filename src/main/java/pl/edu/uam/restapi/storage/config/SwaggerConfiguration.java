package pl.edu.uam.restapi.storage.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.*;
import static com.google.common.base.Predicates.*;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("restapi-storage")
                .select()
                .paths(paths())
                .build();
    }

    private Predicate<String> paths() {
        return not(regex("/error.*"));
    }
}
