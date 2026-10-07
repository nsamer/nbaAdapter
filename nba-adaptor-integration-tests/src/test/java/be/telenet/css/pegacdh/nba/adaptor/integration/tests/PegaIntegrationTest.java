package be.telenet.css.pegacdh.nba.adaptor.integration.tests;

import be.telenet.css.pegacdh.nba.cdh.CdhRequest;
import be.telenet.css.pegacdh.nba.cdh.CdhResponse;
import be.telenet.css.pegacdh.nba.cdh.nbaclient.NextBestActionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class PegaIntegrationTest extends AbstractIntegrationTest {

    private static final String CONTAINER_NAME = "GetNBA";
    private static final String CALL_DIRECTION = "Inbound";
    private static final String CONTEXT_NAME = "Customer";

    @Autowired
    private NextBestActionService nextBestActionService;

    @Test
    void should_return_correct_cdhresponse_for_advisor() {

        CdhRequest cdhRequest = CdhRequest.builder()
                .subjectId(CUSTOMER_NUMBER)
                .direction(CALL_DIRECTION)
                .containerName(CONTAINER_NAME)
                .channel(VALUE_CHANNEL_FIXIT_ADVISOR_TOOL)
                .contextName(CONTEXT_NAME)
                .build();
        CdhResponse cdhResponse = nextBestActionService.getNextBestAction(cdhRequest, "");

        assertThat(cdhResponse).isNotNull();
        assertThat(cdhResponse.getContainerList()).isNotNull();
        assertThat(cdhResponse.getContainerList()).hasSize(1);
        assertThat(cdhResponse.getContainerList().get(0).getRankedResults()).isNotNull();
        assertThat(cdhResponse.getContainerList().get(0).getRankedResults()).hasSize(2);
    }

    @Test
    void should_return_correct_cdhresponse_for_web() {

        CdhRequest cdhRequest = CdhRequest.builder()
                .subjectId(CUSTOMER_NUMBER)
                .direction(CALL_DIRECTION)
                .containerName(CONTAINER_NAME)
                .channel(VALUE_CHANNEL_WEB)
                .contextName(CONTEXT_NAME)
                .build();
        CdhResponse cdhResponse = nextBestActionService.getNextBestAction(cdhRequest, "");

        assertThat(cdhResponse).isNotNull();
        assertThat(cdhResponse.getContainerList()).isNotNull();
        assertThat(cdhResponse.getContainerList()).hasSize(1);
        assertThat(cdhResponse.getContainerList().get(0).getRankedResults()).isNotNull();
        assertThat(cdhResponse.getContainerList().get(0).getRankedResults()).hasSize(2);
    }

}
