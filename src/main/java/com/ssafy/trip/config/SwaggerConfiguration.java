package com.ssafy.trip.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Trip 명세서")
                        .version("1.0.0")
                        .description("Enjoy Trip API Reference Document")
                        .contact(new Contact()
                                .name("Enjoy trip API")
                                .email("aruesin2@gmail.com")
                                .url("https://www.google.com"))
                );
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("user")
                .pathsToMatch("/api/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi postApi() {
        return GroupedOpenApi.builder()
                .group("post")
                .pathsToMatch("/api/post/**")
                .build();
    }

    @Bean
    public GroupedOpenApi travelApi() {
        return GroupedOpenApi.builder()
                .group("travel-diary")
                .pathsToMatch("/api/travel-diary/**")
                .build();
    }

    @Bean
    public GroupedOpenApi hashtagApi() {
        return GroupedOpenApi.builder()
                .group("hashtag")
                .pathsToMatch("/api/hashtag/**")
                .build();
    }

    @Bean
    public GroupedOpenApi tripApi() {
        return GroupedOpenApi.builder()
                .group("trip")
                .pathsToMatch("/api/trip/**")
                .build();
    }

    @Bean
    public GroupedOpenApi graphApi() {
        return GroupedOpenApi.builder()
                .group("graph")
                .pathsToMatch("/api/graph/**")
                .build();
    }
}
