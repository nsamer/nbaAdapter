package be.telenet.css.pegacdh.nba.exception;

public enum ErrorCode {

    CDH_TECHNICAL_ERROR("Unable to process PEGA CDH response."),
    CDH_CONNECTION_ERROR("Request to PEGA CDH failed."),
    INVALID_REQUEST("Request does not contain mandatory parameters.");

    private String description;

    public String getDescription() {
        return this.description;
    }

    ErrorCode(String description) {
        this.description = description;
    }

}
