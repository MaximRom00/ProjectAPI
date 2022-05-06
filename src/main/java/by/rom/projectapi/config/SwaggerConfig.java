package by.rom.projectapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("by.rom.projectapi"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiinfo());
    }

    private ApiInfo apiinfo() {
        return new ApiInfo("Trello Api",
                            "Work with trello board",
                            "1.00",
                            "http://localhost:8081/api/board",
                            new Contact("Max", "www.example.com", "example@gmail.com"),
                            "personal license",
                    "www.example.com",
                            Collections.emptyList());
    }
}
