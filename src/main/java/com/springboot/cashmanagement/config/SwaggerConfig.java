package com.springboot.cashmanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

import io.swagger.v3.oas.models.info.License;


import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cash Account API")
                        .version("1.0")
                        .description("API for managing cash accounts and transactions")
                        .contact(new Contact().name("Samuel").email("arulsam8@gmail.com"))
                        .license(new License().name("Apache 2.0").url("http://localhost:8080/")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder().group("public-api").pathsToMatch("/api/**").build();
    }
}