package be.telenet.css.pegacdh.nba.adaptor.integration.tests;

import be.telenet.css.pegacdh.nba.ResourceAvailableHealthIndicator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class PegaHealthCheckIntegrationTest extends AbstractIntegrationTest {

    @Value("${cdh.healthUrl}")
    private String cdhHealthUrl;

    @Value("${cdh.baseUrl}")
    private String cdhBaseUrl;

    @Autowired
    private ResourceAvailableHealthIndicator resourceAvailableHealthIndicator;

    @Test
    void doCdhHealthCheck_CdhHealthy() throws Exception {
        String healthyUrl = cdhHealthUrl.concat("/healthy");
        resourceAvailableHealthIndicator = new ResourceAvailableHealthIndicator(healthyUrl, true, HttpStatus.OK.value());
        Health.Builder healthBuilder = new Health.Builder();

        resourceAvailableHealthIndicator.doHealthCheck(healthBuilder);

        assertStatus(healthBuilder, healthyUrl, Status.UP);
    }

    @Test
    void doCdhHealthCheck_CdhUnHealthy() throws Exception {
        String unHealthyUrl = cdhHealthUrl.concat("/unhealthy");
        resourceAvailableHealthIndicator = new ResourceAvailableHealthIndicator(unHealthyUrl, true, HttpStatus.OK.value());
        Health.Builder healthBuilder = new Health.Builder();

        resourceAvailableHealthIndicator.doHealthCheck(healthBuilder);

        assertStatus(healthBuilder, unHealthyUrl, Status.DOWN);

    }

    @Test
    void doCdhHealthCheck_withMock() throws Exception {
        String mockUrl = cdhBaseUrl.concat("/customer_reco");
        resourceAvailableHealthIndicator = new ResourceAvailableHealthIndicator(mockUrl, false, HttpStatus.OK.value());
        Health.Builder healthBuilder = new Health.Builder();

        resourceAvailableHealthIndicator.doHealthCheck(healthBuilder);

        assertStatus(healthBuilder, mockUrl, Status.UP);
    }

    private void assertStatus(Health.Builder healthBuilder, String url, Status status) {
        assertThat(healthBuilder).isNotNull();
        assertThat(healthBuilder.build()).isNotNull();
        assertThat(healthBuilder.build().getStatus()).isEqualTo(status);
        assertThat(healthBuilder.build().getDetails()).isNotNull();
        assertThat(healthBuilder.build().getDetails().get("httpStatus").toString()).hasToString("200");
        assertThat(healthBuilder.build().getDetails().get("url")).hasToString(url);
    }

}
