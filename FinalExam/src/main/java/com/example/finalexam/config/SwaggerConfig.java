package com.example.finalexam.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.api.title}")
    private String apiTitle;

    @Value("${swagger.api.version}")
    private String apiVersion;

    @Value("${swagger.api.description}")
    private String apiDescription;

    @Bean
    public OpenAPI initializeSwagger() {
        return new OpenAPI()
                .info(
                        new Info().title(apiTitle)
                                .version(apiVersion)
                                .description(apiDescription)
                );
    }
}
