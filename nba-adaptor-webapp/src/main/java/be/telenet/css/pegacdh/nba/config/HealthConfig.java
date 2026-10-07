package be.telenet.css.pegacdh.nba.config;

import be.telenet.css.pegacdh.nba.ResourceAvailableHealthIndicator;
import be.telenet.css.pegacdh.nba.cdh.nbaclient.NextBestActionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class HealthConfig {

    private final String mockUrl;
    private final String cdhUrl;

    public HealthConfig(NextBestActionService nextBestActionService,
                        @Value("${cdh.healthUrl}") String cdhUrl) {
        this.mockUrl = nextBestActionService.getUrl();
        this.cdhUrl = cdhUrl;
    }

    @Bean
    @ConditionalOnProperty(value = "cdh.source", havingValue = "MOCK")
    public ResourceAvailableHealthIndicator mockHealthIndicator() {
        return new ResourceAvailableHealthIndicator(mockUrl, false, HttpStatus.OK.value());
    }

    @Bean
    @ConditionalOnProperty(value = "cdh.source", havingValue = "CDH")
    public ResourceAvailableHealthIndicator cdhHealthIndicator() {
        return new ResourceAvailableHealthIndicator(cdhUrl, true, HttpStatus.OK.value());
    }

}
