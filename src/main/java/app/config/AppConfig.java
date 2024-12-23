package app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${openai.api.url}")
    private String openAiUrl;

    @Value("${openai.api.key}")
    private String openAiKey;

    @Bean
    public IntegrationsConfig integrationsConfig() {
        return new IntegrationsConfig(openAiUrl, openAiKey);
    }
}


