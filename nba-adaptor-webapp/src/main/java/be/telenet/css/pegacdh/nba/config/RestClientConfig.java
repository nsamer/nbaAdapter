package be.telenet.css.pegacdh.nba.config;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.time.Duration;

public abstract class RestClientConfig { //NOSONAR

    private static final String DEFAULT_USER_AGENT = "NBA Adaptor (Telenet; eDev)";
    private static final int DEFAULT_TOTAL_CONNECTIONS = 50;

    public static HttpComponentsClientHttpRequestFactory createClientHttpRequestFactory(int readTimeout, int connectTimeout) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        HttpClient poolingHttpClient = createPoolingHttpClient(
                Duration.ofMillis(connectTimeout), Duration.ofMillis(readTimeout));
        factory.setHttpClient(poolingHttpClient);

        return factory;
    }

    private static HttpClient createPoolingHttpClient(Duration connectTimeout, Duration readTimeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.of(connectTimeout))
                .setResponseTimeout(Timeout.of(readTimeout))
                .build();

        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setConnectTimeout(Timeout.of(connectTimeout))
                .setSocketTimeout(Timeout.of(readTimeout))
                .build();


        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(DEFAULT_TOTAL_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(DEFAULT_TOTAL_CONNECTIONS);
        connectionManager.setDefaultConnectionConfig(connectionConfig);

        return HttpClientBuilder
                .create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .disableCookieManagement()
                .setUserAgent(DEFAULT_USER_AGENT)
                .build();
    }
}
