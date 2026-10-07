package be.telenet.css.pegacdh.nba.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NextBestActionDto {

    @JsonProperty("rank")
    private int rank;

    @JsonProperty("group")
    private String group;

    @JsonProperty("issue")
    private String issue;

    @JsonProperty("name")
    private String name;

    @JsonProperty("treatment")
    private String treatment;

    @JsonProperty("treatmentCategory")
    private String treatmentCategory;

    @JsonProperty("campaignId")
    private String campaignId;

    @JsonProperty("interactionId")
    private String interactionId;

    @JsonProperty("direction")
    private String direction;

    @JsonProperty("productType")
    private String productType;

    @JsonProperty("shortDescription")
    private String shortDescription;

    @JsonProperty("sellingArguments")
    private List<SellingKey> sellingArguments;

    @JsonProperty("sdata")
    private Object sdata;

    @JsonProperty("sdataEncoded")
    private String sdataEncoded;

    @JsonProperty("locationId")
    private String locationId;

    @JsonProperty("offerDetails")
    private List<OfferDetailDto> offerDetails;

}
