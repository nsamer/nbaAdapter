package be.telenet.css.pegacdh.nba.cdh;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfferDetail {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Treatment")
    private String treatment;

    @JsonProperty("TreatmentFragments")
    private List<TreatmentFragment> treatmentFragments;
}
