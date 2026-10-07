package be.telenet.css.pegacdh.nba.adaptor.integration.tests.nbaclient;

import be.telenet.css.pegacdh.nba.adaptor.integration.tests.AbstractIntegrationTest;
import be.telenet.css.pegacdh.nba.mapper.NbaRequestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(properties = "cdh.source=MOCK")
class MockNextBestActionIntegrationTest extends AbstractIntegrationTest {

    @Test
    void should_return_ok_if_request_from_fixit_channel() throws Exception {
        mockMvc.perform(
                get(GET_NBA_V1_URL, CUSTOMER_NUMBER)
                        .queryParam(PARAM_CHANNEL, VALUE_CHANNEL_FIXIT_ADVISOR_TOOL)
                        .header(NbaRequestMapper.HEADER_BRAND, VALUE_BRAND))
                .andExpect(status().isOk());
    }

    @Test
    void should_return_ok_if_request_from_web_channel() throws Exception {
        mockMvc.perform(
                get(GET_NBA_V1_URL, CUSTOMER_NUMBER)
                        .queryParam(PARAM_CHANNEL, VALUE_CHANNEL_WEB)
                        .header(NbaRequestMapper.HEADER_BRAND, VALUE_BRAND))
                .andExpect(status().isOk());
    }

}
