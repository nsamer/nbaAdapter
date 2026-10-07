package be.telenet.css.pegacdh.nba.mapper;

import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

import static java.util.Objects.nonNull;

public interface NbaRequestContext {

    String HEADER_CORRELATION_ID = "correlationId";
    String HEADER_REQUEST_ID = "requestId";
    String HEADER_MESSAGE_ID = "messageId";
    String HEADER_BRAND = "brand";
    String HEADER_X_SOURCE = "X-source";

    default String getCorrelationId(HttpServletRequest httpServletRequest) {
        String correlationId = httpServletRequest.getHeader(HEADER_CORRELATION_ID);
        return nonNull(correlationId) ? correlationId : UUID.randomUUID().toString();
    }

    default String getRequestId(HttpServletRequest httpServletRequest) {
        String requestId = httpServletRequest.getHeader(HEADER_REQUEST_ID);
        return nonNull(requestId) ? requestId : UUID.randomUUID().toString();
    }

    default String getBrand(HttpServletRequest httpServletRequest) {
        String brand = httpServletRequest.getHeader(HEADER_BRAND);
        return nonNull(brand) ? brand : httpServletRequest.getHeader(HEADER_X_SOURCE);
    }

    default String getDirection(String direction, String cdhDirection) {
        return nonNull(direction) ? direction : cdhDirection;
    }

    default String getMessageId(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader(HEADER_MESSAGE_ID);
    }
}
