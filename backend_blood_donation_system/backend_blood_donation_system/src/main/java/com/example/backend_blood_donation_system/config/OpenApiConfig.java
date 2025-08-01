package com.example.backend_blood_donation_system.config;


import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
public OpenAPI customOpenAPI() {
    // Thêm URL Railway chính xác của bạn vào đây
    Server server = new Server();
    server.setUrl("https://back-end-blood-donation-support-system-production.up.railway.app");

    return new OpenAPI()
            .info(new Info().title("Blood Donation API")
                    .version("1.0")
                    .description("Swagger config cho hệ thống hiến máu"))
            .servers(List.of(server))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new Components()
                    .addSecuritySchemes("bearerAuth", new SecurityScheme()
                            .name("Authorization")
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            );
}
}
