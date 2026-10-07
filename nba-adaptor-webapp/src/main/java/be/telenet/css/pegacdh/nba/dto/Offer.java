package be.telenet.css.pegacdh.nba.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Offer {

    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("amountOfLimitedSims")
    private Integer amountOfLimitedSims;

    @JsonProperty("offers")
    private List<Offer> offers;
}
