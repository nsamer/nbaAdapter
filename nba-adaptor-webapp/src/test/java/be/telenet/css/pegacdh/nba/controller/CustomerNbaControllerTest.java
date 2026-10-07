package be.telenet.css.pegacdh.nba.controller;

import be.telenet.css.pegacdh.nba.dto.NbaDto;
import be.telenet.css.pegacdh.nba.dto.NextBestActionDto;
import be.telenet.css.pegacdh.nba.dto.NextBestActionForCustomerDto;
import be.telenet.css.pegacdh.nba.mapper.NbaRequestMapper;
import be.telenet.css.pegacdh.nba.service.NbaService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CustomerNbaControllerTest {

    public static final String CUSTOMER_NUMBER = "1234";
    public static final String INTERACTION_ID = "interactionid";
    private static final String CHANNEL_FIXIT_ADVISOR_TOOL = "FixitAdvisorTool";
    private static final String CHANNEL_WEB = "Web";
    private static final String HEADER_CORRELATION_ID = "correlationId";
    private static final String HEADER_REQUEST_ID = "requestId";
    private static final String HEADER_MESSAGE_ID = "messageId";
    private static final String HEADER_BRAND = "brand";
    private static final String CONTAINER_NAME = "getNBA";
    private static final String ACTION_NAME = "SAMI_WIGO_35GB_PP";
    private static final String CORRELATION_ID = "1234567890";
    private static final String REQUEST_ID = "30981238945";
    private static final String MESSAGE_ID = "45678901234";
    private static final String BRAND = "Telenet";
    private static final String HEADER_X_SOURCE = "X-source";
    private static final String CONTEXT_NAME = "customer";
    private static final String DIRECTION_INBOUND = "Inbound";

    private NbaService nbaService;
    private NbaRequestMapper nbaRequestMapper;
    private HttpServletRequest httpServletRequest;

    private MockHttpServletRequest mockHttpServletRequest;

    CustomerNbaController customerNbaController;

    @BeforeEach
    public void setup() {
        nbaService = Mockito.mock(NbaService.class);
        nbaRequestMapper = new NbaRequestMapper();
        customerNbaController = new CustomerNbaController(nbaService, nbaRequestMapper);
        httpServletRequest = Mockito.mock(HttpServletRequest.class);
        mockHttpServletRequest = new MockHttpServletRequest();
        setupHeaderValues();
        httpServletRequest = mockHttpServletRequest;
    }

    @Test
    void givenCustomer_shouldReturnNextBestOffer_for_advisor() {
        setUpServiceMock(CHANNEL_FIXIT_ADVISOR_TOOL);
        ResponseEntity<NbaDto> response =  customerNbaController.nextBestAction(CUSTOMER_NUMBER, CHANNEL_FIXIT_ADVISOR_TOOL, CONTEXT_NAME, DIRECTION_INBOUND, httpServletRequest);

       assertResponse(response, CHANNEL_FIXIT_ADVISOR_TOOL);
        assertBrand(HEADER_BRAND);
    }

    @Test
    void givenCustomer_shouldReturnNextBestOffer_web() {
        setUpServiceMock(CHANNEL_WEB);
        ResponseEntity<NbaDto> response =  customerNbaController.nextBestAction(CUSTOMER_NUMBER, CHANNEL_WEB, CONTEXT_NAME, DIRECTION_INBOUND, httpServletRequest);

        assertResponse(response, CHANNEL_WEB);
        assertBrand(HEADER_BRAND);
    }

    @Test
    void givenCustomer_shouldReturnNextBestOffer_web_whenBrandIsPassedWithXSource() {
        mockHttpServletRequest.removeHeader(HEADER_BRAND);
        mockHttpServletRequest.addHeader(HEADER_X_SOURCE, BRAND);
        setUpServiceMock(CHANNEL_WEB);
        ResponseEntity<NbaDto> response =  customerNbaController.nextBestAction(CUSTOMER_NUMBER, CHANNEL_WEB, CONTEXT_NAME, DIRECTION_INBOUND, httpServletRequest);

        assertResponse(response, CHANNEL_WEB);
        assertBrand(HEADER_X_SOURCE);
    }

    private void setupHeaderValues() {
        mockHttpServletRequest.addHeader(HEADER_CORRELATION_ID, CORRELATION_ID);
        mockHttpServletRequest.addHeader(HEADER_MESSAGE_ID, MESSAGE_ID);
        mockHttpServletRequest.addHeader(HEADER_REQUEST_ID, REQUEST_ID);
        mockHttpServletRequest.addHeader(HEADER_BRAND, BRAND);
    }

    private void setUpServiceMock(String channel) {
        when(nbaService.getNextBestAction(any())).thenReturn(NbaDto
                .builder()
                .nextBestActions(NextBestActionForCustomerDto.builder()
                        .customerNumber(CUSTOMER_NUMBER)
                        .interactionId(INTERACTION_ID)
                        .containerName(CONTAINER_NAME)
                        .channel(channel)
                        .actions(List.of(NextBestActionDto.builder()
                                .rank(1)
                                .name(ACTION_NAME)
                                .build()))
                        .build())
                .build());
    }

    private void assertResponse(ResponseEntity<NbaDto> response, String channel) {
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNextBestActions()).isNotNull();
        assertThat(response.getBody().getNextBestActions().getCustomerNumber()).isEqualTo(CUSTOMER_NUMBER);
        assertThat(response.getBody().getNextBestActions().getContainerName()).isEqualTo(CONTAINER_NAME);
        assertThat(response.getBody().getNextBestActions().getChannel()).isEqualTo(channel);
        assertThat(response.getBody().getNextBestActions().getInteractionId()).isEqualTo(INTERACTION_ID);
        assertThat(response.getBody().getNextBestActions().getActions()).isNotNull();
        assertThat(response.getBody().getNextBestActions().getActions().size()).isEqualTo(1);
        assertThat(response.getBody().getNextBestActions().getActions().getFirst().getRank()).isEqualTo(1);
        assertThat(response.getBody().getNextBestActions().getActions().getFirst().getName()).isEqualTo(ACTION_NAME);
        assertThat(httpServletRequest.getHeader(HEADER_CORRELATION_ID)).isEqualTo(CORRELATION_ID);
        assertThat(httpServletRequest.getHeader(HEADER_REQUEST_ID)).isEqualTo(REQUEST_ID);
        assertThat(httpServletRequest.getHeader(HEADER_MESSAGE_ID)).isEqualTo(MESSAGE_ID);
    }

    private void assertBrand(String brandHeaderParam) {
        assertThat(httpServletRequest.getHeader(brandHeaderParam)).isEqualTo(BRAND);
    }

}
