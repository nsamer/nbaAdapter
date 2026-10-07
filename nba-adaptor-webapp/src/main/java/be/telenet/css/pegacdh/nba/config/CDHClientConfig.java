package be.telenet.css.pegacdh.nba.config;

import be.telenet.css.pegacdh.nba.cdh.CdhMockServer;
import be.telenet.css.pegacdh.nba.cdh.nbaclient.CdhNextBestActionClient;
import be.telenet.css.pegacdh.nba.cdh.nbaclient.MockNextBestActionClient;
import be.telenet.css.pegacdh.nba.common.config.CdhHeaderProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CdhHeaderProperties.class)
@Slf4j
public class CDHClientConfig extends RestClientConfig {

    private static final String PORT_PLACEHOLDER = "PORT";

    private final String baseUrl;
    private final int readTimeout;
    private final int connectTimeout;
    private final ObjectMapper objectMapper;

    public CDHClientConfig(
            @Value("${cdh.baseUrl}") String baseUrl,
            @Value("${cdh.timeout.read:5000}") int readTimeout,
            @Value("${cdh.timeout.connect:2000}") int connectTimeout,
            ObjectMapper objectMapper) {
        this.baseUrl = baseUrl;
        this.readTimeout = readTimeout;
        this.connectTimeout = connectTimeout;
        this.objectMapper = objectMapper;
    }

    @Bean
    @ConditionalOnProperty(value = "cdh.source", havingValue = "MOCK")
    public MockNextBestActionClient mockNextBestActionClient(CdhMockServer cdhMockServer){
        String mockBaseUrl = this.baseUrl.replaceFirst(PORT_PLACEHOLDER, String.valueOf(cdhMockServer.getWireMockServerPort()));
        log.info("creating mockNextBestActionClient with [url={}], {} read, {} connect timeout", mockBaseUrl, readTimeout, connectTimeout);
        return new MockNextBestActionClient(mockBaseUrl, createClientHttpRequestFactory(readTimeout, connectTimeout), objectMapper);
    }

    @Bean
    @ConditionalOnProperty(value = "cdh.source", havingValue = "CDH")
    public CdhNextBestActionClient cdhNextBestActionClient(){
        log.info("creating cdhNextBestActionClient with [url={}], {} read, {} connect timeout", baseUrl, readTimeout, connectTimeout);
        return new CdhNextBestActionClient(baseUrl, createClientHttpRequestFactory(readTimeout, connectTimeout), objectMapper);
    }
}
