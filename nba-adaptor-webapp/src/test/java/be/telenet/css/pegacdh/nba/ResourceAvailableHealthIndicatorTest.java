package be.telenet.css.pegacdh.nba;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ResourceAvailableHealthIndicatorTest {

    private static final String CDH_HEALTH_CHECK_MOCK_URL = "http://127.0.0.1:8065";
    private ResourceAvailableHealthIndicator resourceAvailableHealthIndicator;
    private Health.Builder healthBuilder;

    @BeforeEach
    public void setup() {
        healthBuilder = new Health.Builder();
    }

    @Test
    void doCdhHealthCheck_CdhError() throws Exception {
        resourceAvailableHealthIndicator = new ResourceAvailableHealthIndicator(CDH_HEALTH_CHECK_MOCK_URL, true, HttpStatus.OK.value());

        resourceAvailableHealthIndicator.doHealthCheck(healthBuilder);

        assertThat(healthBuilder).isNotNull();
        assertThat(healthBuilder.build()).isNotNull();
        assertThat(healthBuilder.build().getStatus()).isEqualTo(Status.DOWN);
        assertThat(healthBuilder.build().getDetails()).isNotNull();
        assertThat(healthBuilder.build().getDetails().get("httpStatus").toString()).hasToString(HttpStatus.EXPECTATION_FAILED.toString());
        assertThat(healthBuilder.build().getDetails().get("url")).hasToString(CDH_HEALTH_CHECK_MOCK_URL);
    }

}