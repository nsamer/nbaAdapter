package be.telenet.css.pegacdh.nba.mapper;

import be.telenet.css.pegacdh.nba.dto.NbaRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NbaRequestMapper implements NbaRequestContext {

    @Value("${cdh.direction}")
    private String cdhDirection;

    public NbaRequest map(String customerNumber, String channel, String contextName, String direction, HttpServletRequest httpServletRequest) {
        return NbaRequest.builder()
                .customerNumber(customerNumber)
                .channel(channel)
                .callDirection(getDirection(direction, cdhDirection))
                .correlationId(getCorrelationId(httpServletRequest))
                .requestId(getRequestId(httpServletRequest))
                .messageId(getMessageId(httpServletRequest))
                .brand(getBrand(httpServletRequest))
                .contextName(!StringUtils.isEmpty(contextName) ? contextName : "Customer")
                .build();
    }

}