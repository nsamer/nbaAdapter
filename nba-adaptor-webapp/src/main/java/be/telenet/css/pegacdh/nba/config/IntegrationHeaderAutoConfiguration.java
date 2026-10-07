package be.telenet.css.pegacdh.nba.config;

import be.telenet.css.pegacdh.nba.cdh.integrationheader.IntegrationHeaderProperties;
import be.telenet.css.pegacdh.nba.cdh.integrationheader.IntegrationHeaderService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(IntegrationHeaderProperties.class)
public class IntegrationHeaderAutoConfiguration {

    @Bean
    public IntegrationHeaderService integrationHeaderService(IntegrationHeaderProperties integrationHeaderProperties) {
        return new IntegrationHeaderService(integrationHeaderProperties);
    }

}
