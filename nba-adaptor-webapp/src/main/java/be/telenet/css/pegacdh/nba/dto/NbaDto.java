package be.telenet.css.pegacdh.nba.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper=false)
public class NbaDto extends ResponseDto {

    @JsonProperty("nextBestActions")
    private NextBestActionForCustomerDto nextBestActions;

}
