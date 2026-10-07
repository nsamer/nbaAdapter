package be.telenet.css.pegacdh.nba.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

import static be.telenet.css.pegacdh.nba.exception.ErrorCode.CDH_CONNECTION_ERROR;

@RequiredArgsConstructor
public class CdhErrorHandler extends DefaultResponseErrorHandler {

    private final ObjectMapper objectMapper;

    @Override
    protected void handleError(ClientHttpResponse response, HttpStatusCode statusCode, URI url, HttpMethod method) throws IOException {
        try {
            byte[] body = getResponseBody(response);
            var errorResponse = objectMapper.readValue(body, ErrorResponse.class);
            throw new CdhException(CDH_CONNECTION_ERROR,  errorResponse.getMessage());
        } catch (JsonProcessingException e) {
            throw new IOException("Cannot parse error response", e);
        }
    }

    @Data
    private static class ErrorResponse {
        @JsonProperty("Status")
        String status;
        @JsonProperty("Message")
        String message;
    }
}
