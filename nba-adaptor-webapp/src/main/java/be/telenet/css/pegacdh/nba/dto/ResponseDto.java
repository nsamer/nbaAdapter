package be.telenet.css.pegacdh.nba.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private String code;
    private String cause;
}
