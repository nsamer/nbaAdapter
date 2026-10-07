package be.telenet.css.pegacdh.nba.cdh.nbaclient;

import be.telenet.css.pegacdh.nba.cdh.CdhRequest;
import be.telenet.css.pegacdh.nba.cdh.CdhResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;

import java.net.URI;

public class MockNextBestActionClient extends AbstractNextBestActionClient {

    private static final String CDH_NEXT_BEST_ACTION = "/customer_reco";
    private static final String REQUEST_PARAM_INITIAL = "?";
    private static final String REQUEST_PARAM_SEPARATOR = "&";
    private static final String CDH_NEXT_BEST_MOCK_NBO_PARAM = "mockNba=37785";
    private static final String CDH_NEXT_BEST_MOCK_CHANNEL_PARAM = "channel=Web";

    public MockNextBestActionClient(String baseUrl, ClientHttpRequestFactory requestFactory, ObjectMapper objectMapper) {
        super(baseUrl, requestFactory, objectMapper);
    }

    @Override
    public URI getUri() {
        String url = getBaseUrl()
                .concat(CDH_NEXT_BEST_ACTION)
                .concat(REQUEST_PARAM_INITIAL)
                .concat(CDH_NEXT_BEST_MOCK_NBO_PARAM)
                .concat(REQUEST_PARAM_SEPARATOR)
                .concat(CDH_NEXT_BEST_MOCK_CHANNEL_PARAM);

        return URI.create(url);
    }

    @Override
    public CdhResponse getNextBestAction(CdhRequest request, String header) {
        try {
            return super.getNextBestAction(request, header);
        } catch (ResourceAccessException e) {
            logger.warn("Request failed because of flaky WireMockServer, retrying once.", e);
            return super.getNextBestAction(request, header);
        }
    }
}
