package com.bdu.clearance.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

        private static final String BEARER_SCHEME = "bearerAuth";

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("BDU Student Clearance API")
                                                .version("2.0")
                                                .description("Student Clearance & Resource Management System - Bahir Dar University")
                                                .contact(new Contact()
                                                                .name("BDU IT")
                                                                .email("support@bdu.edu.et")))
                                .addSecurityItem(new SecurityRequirement().addList(BEARER_SCHEME))
                                .components(new Components()
                                                .addSecuritySchemes(BEARER_SCHEME,
                                                                new SecurityScheme()
                                                                                .type(SecurityScheme.Type.HTTP)
                                                                                .scheme("bearer")
                                                                                .bearerFormat("JWT")
                                                                                .description("Enter your JWT token to authenticate requests")));
        }
}
