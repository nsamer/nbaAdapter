package be.telenet.css.pegacdh.nba.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NbaRequest {
    @NonNull
    private String customerNumber;
    @NonNull
    private String channel;
    private String callDirection;
    private String correlationId;
    private String requestId;
    private String messageId;
    @NonNull
    private String brand;
    private String contextName;
}
