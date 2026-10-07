package be.telenet.css.pegacdh.nba;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
public class ResourceAvailableHealthIndicator extends AbstractHealthIndicator {

    private static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 2000;
    private static final int DEFAULT_READ_TIMEOUT_MILLIS = 2000;
    private static final String USER_AGENT_HEADER_KEY = "User-Agent";
    private static final String USER_AGENT_HEADER_VALUE = "HealthCheck/1.0 (Telenet; NBA Adaptor)";
    private static final String CDH_HEALTH_STATE_KEY = "state";
    private static final String CDH_HEALTH_STATE_VALUE = "healthy";
    private static final String HTTP_STATUS = "httpStatus";

    private final String url;
    private final List<Integer> allowedStatuses;
    private final boolean cdh;

    public ResourceAvailableHealthIndicator(String url, boolean cdh, Integer... allowedStatuses) {
        this.url = url;
        this.allowedStatuses = Arrays.asList(allowedStatuses);
        this.cdh = cdh;
    }

    @Override
    public void doHealthCheck(Health.Builder builder) throws Exception {
        builder.withDetail("url", url);
        if (cdh) {
            doCdhHealthCheck(builder);
        } else {
            doMockHealthCheck(builder);
        }
    }

    protected void doCdhHealthCheck(Health.Builder builder) {
        try {
            HttpResponse<String> response = getResponse();
            builder.withDetail(HTTP_STATUS, response.statusCode());
            if(isAllowedStatus(response.statusCode()) && isCdhHealthy(response.body())) {
                builder.up();
            } else {
                log.error("CDH [url={}] is not healthy [status={}] [responseBody={}]", url, response.statusCode(), response.body());
                builder.down();
            }
        } catch (Exception e) {
            log.error("Error occurred while connecting to CDH [url={}] [error={}]", url, e.getMessage(), e);
            builder.withDetail(HTTP_STATUS, HttpStatus.EXPECTATION_FAILED);
            builder.down(e.getCause());
        }

    }

    protected void doMockHealthCheck(Health.Builder builder) throws IOException, InterruptedException {
        int status = getHttpStatus();
        builder.withDetail(HTTP_STATUS, status);
        if (isAllowedStatus(status)) {
            builder.up();
        } else {
            builder.down();
        }
    }

    protected HttpResponse<String> getResponse() throws IOException, InterruptedException {
        var httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(DEFAULT_CONNECT_TIMEOUT_MILLIS))
                .build();

        var request = HttpRequest.newBuilder().GET()
                .uri(URI.create(url))
                .setHeader(USER_AGENT_HEADER_KEY, USER_AGENT_HEADER_VALUE)
                .timeout(Duration.ofMillis(DEFAULT_READ_TIMEOUT_MILLIS))
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private int getHttpStatus() throws IOException, InterruptedException {
        return getResponse().statusCode();
    }

    private boolean isAllowedStatus(int httpStatus) {
        return allowedStatuses.contains(httpStatus);
    }

    private boolean isCdhHealthy(String responseBody) {
        if(nonNull(responseBody)) {
            Object healthStateValue = new GsonJsonParser().parseMap(responseBody).get(CDH_HEALTH_STATE_KEY);
            return nonNull(healthStateValue) && CDH_HEALTH_STATE_VALUE.equalsIgnoreCase(healthStateValue.toString());
        }
        return false;
    }

}
