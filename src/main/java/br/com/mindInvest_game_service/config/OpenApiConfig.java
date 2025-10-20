package br.com.mindInvest_game_service.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI mindInvestOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MindInvest Game API")
                        .description("API para gerenciamento de rodadas e personagens do jogo educativo MindInvest.")
                        .version("1.0.0")
                        .license(new License().name("MIT License").url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositório no GitHub")
                        .url("https://github.com/LucasRDelfino/mindinvest-game-service"));
    }
}
