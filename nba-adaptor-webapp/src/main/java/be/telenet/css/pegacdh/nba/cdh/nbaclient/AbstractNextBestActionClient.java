package be.telenet.css.pegacdh.nba.cdh.nbaclient;

import be.telenet.css.pegacdh.nba.exception.CdhErrorHandler;
import be.telenet.css.pegacdh.nba.exception.CdhException;
import be.telenet.css.pegacdh.nba.cdh.CdhRequest;
import be.telenet.css.pegacdh.nba.cdh.CdhResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

import static be.telenet.css.pegacdh.nba.exception.ErrorCode.CDH_TECHNICAL_ERROR;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Getter(AccessLevel.PROTECTED)
@Slf4j(access = AccessLevel.PROTECTED)
abstract class AbstractNextBestActionClient extends RestTemplate implements NextBestActionService {

    private static final String CDH_INTEGRATION_HEADER_KEY = "integrationheader";

    private final String baseUrl;
    private final ObjectMapper objectMapper;

    protected AbstractNextBestActionClient(String baseUrl, ClientHttpRequestFactory requestFactory, ObjectMapper objectMapper) {
        super(requestFactory);
        this.baseUrl = baseUrl;
        this.objectMapper = objectMapper;
        this.setErrorHandler(new CdhErrorHandler(objectMapper));
    }

    public CdhResponse getNextBestAction(CdhRequest request, String header) {
        log.info("Next best action request {} with headers {}", request, header);
        RequestEntity<CdhRequest> cdhRequestRequestEntity = new RequestEntity<>(
                request,
                getHeaders(header),
                HttpMethod.POST,
                getUri());
        ResponseEntity<String> response = exchange(cdhRequestRequestEntity, String.class);
        log.info("Next best action response {}", response);
        return getCDHResponse(response.getBody());
    }

    protected MultiValueMap<String, String> getHeaders(String header) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add(CDH_INTEGRATION_HEADER_KEY, header);
        headers.add(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    public URI getUri() {
        return URI.create(baseUrl);
    }

    public String getUrl(){
        return getUri().toString();
    }

    @SneakyThrows
    private CdhResponse getCDHResponse(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, CdhResponse.class);
        } catch (IOException io) {
            throw new CdhException(CDH_TECHNICAL_ERROR, io);
        }
    }

}
