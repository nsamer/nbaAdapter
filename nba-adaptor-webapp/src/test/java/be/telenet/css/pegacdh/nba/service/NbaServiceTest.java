package be.telenet.css.pegacdh.nba.service;

import be.telenet.css.pegacdh.nba.cdh.CdhRequest;
import be.telenet.css.pegacdh.nba.cdh.CdhRequestBuilder;
import be.telenet.css.pegacdh.nba.cdh.CdhResponse;
import be.telenet.css.pegacdh.nba.cdh.NbaContainer;
import be.telenet.css.pegacdh.nba.cdh.RankedResult;
import be.telenet.css.pegacdh.nba.cdh.integrationheader.IntegrationHeaderProperties;
import be.telenet.css.pegacdh.nba.cdh.integrationheader.IntegrationHeaderService;
import be.telenet.css.pegacdh.nba.cdh.nbaclient.NextBestActionService;
import be.telenet.css.pegacdh.nba.dto.NbaDto;
import be.telenet.css.pegacdh.nba.dto.NbaRequest;
import be.telenet.css.pegacdh.nba.dto.NextBestActionDto;
import be.telenet.css.pegacdh.nba.dto.NextBestActionForCustomerDto;
import be.telenet.css.pegacdh.nba.mapper.NbaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NbaServiceTest {

    public static final String CUSTOMER_NUMBER = "1234";

    private static final String CHANNEL_FIXIT_ADVISOR_TOOL = "FixitAdvisorTool";
    private static final String CORRELATION_ID = "1234567890";
    private static final String REQUEST_ID = "30981238945";
    private static final String MESSAGE_ID = "45678901234";
    private static final String BRAND = "Telenet";
    private static final String CALL_DIRECTION = "Inbound";
    private static final String INTEGRATION_HEADER_ENVIRONMENT = "local";
    private static final String INTEGRATION_HEADER_APPLICATION_NAME = "NBA Adaptor";

    private NbaService nbaService;

    private NextBestActionService nextBestActionService;
    private IntegrationHeaderService integrationHeaderService;
    private CdhRequestBuilder cdhRequestBuilder;
    private NbaMapper nbaMapper;

    @BeforeEach
    public void setup() {
        IntegrationHeaderProperties integrationHeaderProperties = new IntegrationHeaderProperties();
        integrationHeaderProperties.setEnvironment(INTEGRATION_HEADER_ENVIRONMENT);
        integrationHeaderProperties.setApplicationName(INTEGRATION_HEADER_APPLICATION_NAME);
        integrationHeaderService = new IntegrationHeaderService(integrationHeaderProperties);

        nextBestActionService = Mockito.mock(NextBestActionService.class);
        cdhRequestBuilder = Mockito.mock(CdhRequestBuilder.class);
        nbaMapper = Mockito.mock(NbaMapper.class);

        nbaService = new NbaService(nextBestActionService, integrationHeaderService, cdhRequestBuilder, nbaMapper);
    }

    @Test
    void getNextBestOffer_mapToNbaDto() {
        when(cdhRequestBuilder.createRequest(any())).thenReturn(CdhRequest.builder().build());

        CdhResponse res = CdhResponse.builder()
                .containerList(List.of(NbaContainer.builder()
                        .status("OK")
                        .rankedResults(List.of(RankedResult.builder()
                                .build()))
                        .build()))
                .build();
        when(nextBestActionService.getNextBestAction(any(), any())).thenReturn(res);

        when(nbaMapper.map(any(), any())).thenReturn(NbaDto.builder()
                .nextBestActions(NextBestActionForCustomerDto.builder()
                        .customerNumber("1234")
                        .actions(List.of(NextBestActionDto.builder()
                                .build()))
                        .build())
                .build());

        NbaDto nba = nbaService.getNextBestAction(getNbaRequest());

        ArgumentCaptor<String> integrationHeaderCaptor = ArgumentCaptor.forClass(String.class);
        verify(nextBestActionService).getNextBestAction(any(), integrationHeaderCaptor.capture());
        String headerJson = integrationHeaderCaptor.getValue();

        assertThat(nba).isNotNull();
        assertThat(nba.getNextBestActions()).isNotNull();
        assertThat(nba.getNextBestActions().getActions()).isNotNull();
        assertThat(nba.getNextBestActions().getActions()).hasSize(1);
        assertThat(headerJson)
                .contains(INTEGRATION_HEADER_ENVIRONMENT)
                .contains(INTEGRATION_HEADER_APPLICATION_NAME);
    }

    private NbaRequest getNbaRequest() {
        return NbaRequest.builder()
                .customerNumber(CUSTOMER_NUMBER)
                .channel(CHANNEL_FIXIT_ADVISOR_TOOL)
                .callDirection(CALL_DIRECTION)
                .correlationId(CORRELATION_ID)
                .requestId(REQUEST_ID)
                .messageId(MESSAGE_ID)
                .brand(BRAND)
                .build();
    }

}
