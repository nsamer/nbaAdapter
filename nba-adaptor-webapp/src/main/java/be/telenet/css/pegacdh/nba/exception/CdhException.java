package be.telenet.css.pegacdh.nba.exception;

public class CdhException extends RuntimeException {

    private final ErrorCode code;
    private final String message;

    public CdhException(ErrorCode errorCode, String message) {
        super();
        this.code = errorCode;
        this.message = message;
    }

    public CdhException(ErrorCode errorCode, Exception io) {
        super();
        this.code = errorCode;
        this.message = io.getMessage();
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public ErrorCode getCode() {
        return this.code;
    }

}
