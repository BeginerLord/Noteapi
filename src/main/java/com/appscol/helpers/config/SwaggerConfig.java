package com.appscol.helpers.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER
)
@OpenAPIDefinition(
        info = @Info(
                title = "Sistema de Gestión Académica",
                description = "API REST Backend para la gestión académica de una institución educativa.",
                termsOfService = "contacto@institucion.edu",
                version = "1.0.0",
                contact = @Contact(
                        name = "Equipo Dinamita 🧨",
                        url = "https://www.institucion.edu",
                        email = "soporte@institucion.edu"
                ),
                license = @License(
                        name = "Licencia Institucional",
                        url = "https://www.institucion.edu/licencia"
                )
        ),
        servers = {
                @Server(
                        description = "Servidor de Desarrollo",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Servidor de Producción",
                        url = "http://academico.example.com"
                )
        },
        security = {
                @SecurityRequirement(name = "Bearer Authentication")
        }
)
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components());
    }
}
