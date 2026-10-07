package be.telenet.css.pegacdh.nba.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NextBestActionForCustomerDto {

    @JsonProperty("customerNumber")
    private String customerNumber;

    @JsonProperty("interactionId")
    private String interactionId;

    @JsonProperty("channel")
    private String channel;

    @JsonProperty("containerName")
    private String containerName;

    @JsonProperty("actions")
    private List<NextBestActionDto> actions;

    private List<OfferDetailDto> offerDetails;

}
