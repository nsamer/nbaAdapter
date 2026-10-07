package be.telenet.css.pegacdh.nba.cdh.nbaclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.client.ClientHttpRequestFactory;

public class CdhNextBestActionClient extends AbstractNextBestActionClient {

    public CdhNextBestActionClient(String baseUrl, ClientHttpRequestFactory requestFactory, ObjectMapper objectMapper) {
        super(baseUrl, requestFactory, objectMapper);
    }

}
