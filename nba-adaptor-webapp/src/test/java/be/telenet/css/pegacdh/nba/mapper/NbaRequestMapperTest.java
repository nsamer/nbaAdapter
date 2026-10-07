package be.telenet.css.pegacdh.nba.mapper;

import be.telenet.css.pegacdh.nba.dto.NbaRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class NbaRequestMapperTest {

    public static final String CUSTOMER_NUMBER = "1234";
    private static final String CHANNEL_WEB = "Web";
    private static final String HEADER_CORRELATION_ID = "correlationId";
    private static final String HEADER_REQUEST_ID = "requestId";
    private static final String HEADER_MESSAGE_ID = "messageId";
    private static final String HEADER_BRAND = "brand";
    private static final String CORRELATION_ID = "1234567890";
    private static final String REQUEST_ID = "30981238945";
    private static final String MESSAGE_ID = "45678901234";
    private static final String BRAND = "Telenet";
    private static final String HEADER_X_SOURCE = "X-source";
    private static final String CALL_DIRECTION = "Inbound";
    private static final String CALL_DIRECTION_FROM_REQUEST = "Inbound";
    private static final String CONTEXT_NAME = "customer";

    private NbaRequestMapper nbaRequestMapper;
    private HttpServletRequest httpServletRequest;
    private MockHttpServletRequest mockHttpServletRequest;

    @BeforeEach
    public void setup() {
        nbaRequestMapper = new NbaRequestMapper();
        ReflectionTestUtils.setField(nbaRequestMapper, "cdhDirection", CALL_DIRECTION);
        httpServletRequest = Mockito.mock(HttpServletRequest.class);
        mockHttpServletRequest = new MockHttpServletRequest();
        setupHeaderValues();
        httpServletRequest = mockHttpServletRequest;
    }

    @Test
    void mapToNbaRequest() {
        mockHttpServletRequest.addHeader(HEADER_BRAND, BRAND);
        NbaRequest nbaRequest = nbaRequestMapper.map(CUSTOMER_NUMBER, CHANNEL_WEB, CONTEXT_NAME, CALL_DIRECTION_FROM_REQUEST, httpServletRequest);
        assertNbaRequestWithHeaderIds(nbaRequest);
    }

    @Test
    void mapToNbaRequest_whenIdsAreNotPassedInHeader() {
        mockHttpServletRequest.removeHeader(CORRELATION_ID);
        mockHttpServletRequest.removeHeader(MESSAGE_ID);
        mockHttpServletRequest.removeHeader(REQUEST_ID);
        mockHttpServletRequest.addHeader(HEADER_BRAND, BRAND);
        NbaRequest nbaRequest = nbaRequestMapper.map(CUSTOMER_NUMBER, CHANNEL_WEB, CONTEXT_NAME, CALL_DIRECTION_FROM_REQUEST, httpServletRequest);
        assertNbaRequestWithRandomIds(nbaRequest);
    }

    @Test
    void mapToNbaRequest_whenBrandIsPassedInXSource() {
        mockHttpServletRequest.addHeader(HEADER_X_SOURCE, BRAND);
        NbaRequest nbaRequest = nbaRequestMapper.map(CUSTOMER_NUMBER, CHANNEL_WEB, CONTEXT_NAME, CALL_DIRECTION_FROM_REQUEST, httpServletRequest);
        assertNbaRequestWithHeaderIds(nbaRequest);
    }

    @Test
    void mapToNbaRequest_whenDirectIsNotPassedInRequest() {
        mockHttpServletRequest.addHeader(HEADER_X_SOURCE, BRAND);
        NbaRequest nbaRequest = nbaRequestMapper.map(CUSTOMER_NUMBER, CHANNEL_WEB, CONTEXT_NAME, null, httpServletRequest);
        assertNbaRequestWithHeaderIds(nbaRequest);
    }

    private void assertNbaRequestWithHeaderIds(NbaRequest nbaRequest) {
        assertNbaRequest(nbaRequest);
        assertThat(nbaRequest.getCorrelationId()).isEqualTo(CORRELATION_ID);
        assertThat(nbaRequest.getRequestId()).isEqualTo(REQUEST_ID);
        assertThat(nbaRequest.getMessageId()).isEqualTo(MESSAGE_ID);
        assertThat(nbaRequest.getBrand()).isEqualTo(BRAND);
    }

    private void assertNbaRequestWithRandomIds(NbaRequest nbaRequest) {
        assertNbaRequest(nbaRequest);
        assertThat(nbaRequest.getCorrelationId()).isNotNull();
        assertThat(nbaRequest.getRequestId()).isNotNull();
        assertThat(nbaRequest.getMessageId()).isNotNull();
        assertThat(nbaRequest.getBrand()).isEqualTo(BRAND);
    }

    private void assertNbaRequest(NbaRequest nbaRequest) {
        assertThat(nbaRequest).isNotNull();
        assertThat(nbaRequest.getCustomerNumber()).isEqualTo(CUSTOMER_NUMBER);
        assertThat(nbaRequest.getChannel()).isEqualTo(CHANNEL_WEB);
        assertThat(nbaRequest.getContextName()).isEqualTo(CONTEXT_NAME);
        assertThat(nbaRequest.getCallDirection()).isEqualTo(CALL_DIRECTION);
    }

    private void setupHeaderValues() {
        mockHttpServletRequest.addHeader(HEADER_CORRELATION_ID, CORRELATION_ID);
        mockHttpServletRequest.addHeader(HEADER_MESSAGE_ID, MESSAGE_ID);
        mockHttpServletRequest.addHeader(HEADER_REQUEST_ID, REQUEST_ID);
    }
}