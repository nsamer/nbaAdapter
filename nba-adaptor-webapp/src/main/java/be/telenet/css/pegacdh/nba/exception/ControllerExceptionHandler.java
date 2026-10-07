package be.telenet.css.pegacdh.nba.exception;

import be.telenet.css.pegacdh.nba.mapper.NbaRequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static be.telenet.css.pegacdh.nba.exception.ErrorCode.CDH_TECHNICAL_ERROR;
import static be.telenet.css.pegacdh.nba.exception.ErrorCode.INVALID_REQUEST;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CdhException.class)
    public ResponseEntity<ErrorResponse> handleCdhException(CdhException exception, WebRequest webRequest) {

        var errorResponse = new ErrorResponse(exception.getMessage(),
                exception.getCode(),
                exception.getCode().getDescription(),
                getCorrelationId(webRequest));
        log.error("CDH exception occurred with code {}, correlationId {}", exception.getCode(), getCorrelationId(webRequest), exception);
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(IllegalArgumentException exception, WebRequest webRequest) {

        var errorResponse = new ErrorResponse(exception.getMessage(),
                INVALID_REQUEST,
                INVALID_REQUEST.getDescription(),
                getCorrelationId(webRequest));
        log.error("Illegal argument exception occurred with code {}, correlationId {}", INVALID_REQUEST, getCorrelationId(webRequest), exception);
        return  ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception exception, WebRequest webRequest) {
        var errorResponse = new ErrorResponse(exception.getMessage(),
                CDH_TECHNICAL_ERROR,
                CDH_TECHNICAL_ERROR.getDescription(),
                getCorrelationId(webRequest));
        log.error("General exception occurred, correlationId {}", getCorrelationId(webRequest), exception);
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private String getCorrelationId(WebRequest webRequest) {
        return webRequest.getHeader(NbaRequestContext.HEADER_CORRELATION_ID);
    }

}
