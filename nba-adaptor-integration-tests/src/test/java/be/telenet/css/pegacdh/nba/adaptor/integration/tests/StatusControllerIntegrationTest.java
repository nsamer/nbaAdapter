package be.telenet.css.pegacdh.nba.adaptor.integration.tests;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StatusControllerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/status/health")).andExpect(status().isOk());
    }

    @Test
    void testLivenessEndpoint() throws Exception {
        mockMvc.perform(get("/status/health/liveness")).andExpect(status().isOk());
    }

    @Test
    void testReadinessEndpoint() throws Exception {
        mockMvc.perform(get("/status/health/readiness")).andExpect(status().isOk());
    }

    @Test
    void testVersionEndpoint() throws Exception {
        mockMvc.perform(get("/status/version")).andExpect(status().isOk());
    }

}
