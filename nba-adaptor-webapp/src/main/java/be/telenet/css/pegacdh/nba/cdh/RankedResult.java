package be.telenet.css.pegacdh.nba.cdh;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RankedResult {

    @JsonProperty("Channel")
    private String channel;

    @JsonProperty("Group")
    private String group;

    @JsonProperty("Issue")
    private String issue;

    @JsonProperty("InteractionID")
    private String interactionId;

    @JsonProperty("Direction")
    private String direction;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("ShortDescription")
    private String shortDescription;

    @JsonProperty("Rank")
    private int rank;

    @JsonProperty("Treatment")
    private String treatment;

    @JsonProperty("TreatmentCategory")
    private String treatmentCategory;

    @JsonProperty("SellingArguments")
    private String sellingArguments;

    @JsonProperty("CampaignID")
    private String campaignId;

    @JsonProperty("ProductID")
    private String product;

    @JsonProperty("ProductType")
    private String productType;

    @JsonProperty("LocationID")
    private String locationId;

    @JsonProperty("OfferDetails")
    private List<OfferDetail> offerDetails;

}
