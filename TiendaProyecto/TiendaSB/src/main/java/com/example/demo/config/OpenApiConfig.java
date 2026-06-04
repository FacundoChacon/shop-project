package com.example.demo.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(){

        return new OpenAPI()

                .info(new Info()

                        .title("API Tienda")
                        .version("1.0.0")

                        .description("""
                                API REST desarrollada con Spring Boot.
                                
                                Permite:
                                - Crear productos
                                - Listar productos
                                - Actualizar productos
                                - Eliminar productos
                                """)

                        .contact(new Contact()
                                .name("Facundo")
                                .email("chaconfacu2006@email.com"))

                        .license(new License()
                                .name("MIT")))

                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "bearerAuth",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )

                .addSecurityItem(
                        new SecurityRequirement()
                                .addList("bearerAuth")
                )

                .externalDocs(new ExternalDocumentation()
                        .description("Repositorio del proyecto")
                        .url("https://github.com/FacundoChacon/shop-project"));
    }
}