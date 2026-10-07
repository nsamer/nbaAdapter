package be.telenet.css.pegacdh.nba.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    String message;
    ErrorCode code;
    String description;
    String correlationId;
}
