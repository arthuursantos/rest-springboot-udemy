package org.example.restspringbootudemy.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RESTful API com Spring Boot")
                        .version("V1")
                        .description("Projeto que acompanha o desenvolvimento de uma API REST com Spring Boot durante curso produzido pela Erudio Training e disponibilizado na Udemy.")
                        .termsOfService("https://pub.erudio.com.br/meus-cursos")
                        .license(
                                new License()
                                    .name("Apache 2.0")
                                    .url("https://pub.erudio.com.br/meus-cursos")
                                )
                );
    }

}
