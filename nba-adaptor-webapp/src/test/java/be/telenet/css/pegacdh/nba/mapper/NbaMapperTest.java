package be.telenet.css.pegacdh.nba.mapper;

import be.telenet.css.pegacdh.nba.cdh.CdhResponse;
import be.telenet.css.pegacdh.nba.cdh.NbaContainer;
import be.telenet.css.pegacdh.nba.cdh.RankedResult;
import be.telenet.css.pegacdh.nba.dto.NbaDto;
import be.telenet.css.pegacdh.nba.dto.NextBestActionDto;
import be.telenet.css.pegacdh.nba.dto.SellingKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class NbaMapperTest {

    private NbaMapper nbaMapper;

    @BeforeEach
    void setup() {
        nbaMapper = new NbaMapper();
    }

    @Test
    void mapToNbaDto_when_status_isOK() {

        CdhResponse cdhResponse = CdhResponse.builder()
                .status("OK")
                .containerList(List.of(NbaContainer.builder()
                        .status("OK")
                        .rankedResults(List.of(getRankedResult()))
                        .build()))
                .build();

        NbaDto nba = nbaMapper.map(cdhResponse, "1234");

        verifyNbaIsNotNull(nba);
        List<NextBestActionDto> nextBestOffers = nba.getNextBestActions().getActions();
        assertThat(nba.getNextBestActions().getInteractionId()).isEqualTo("INTERACTION-1234567-ID-12113");
        assertThat(nextBestOffers).hasSize(1);
        verifyRankedResultMapToNextBestOfferDto(nextBestOffers.getFirst());
    }

    @Test
    void mapToNbaDto_when_status_isNotOK() {

        CdhResponse cdhResponse = CdhResponse.builder()
                .status("NOK")
                .containerList(List.of(NbaContainer.builder().build()))
                .build();

        NbaDto nba = nbaMapper.map(cdhResponse, "1234");

        assertThat(nba).isNotNull();
        assertThat(nba.getNextBestActions()).isNotNull();
        assertThat(nba.getNextBestActions().getActions()).isNullOrEmpty();
    }

    @Test
    void mapToNbaDto_when_containerStatus_isNull() {

        CdhResponse cdhResponse = CdhResponse.builder()
                .status("OK")
                .containerList(List.of(NbaContainer.builder()
                        .rankedResults(List.of(RankedResult.builder()
                                .build()))
                        .build()))
                .build();

        NbaDto nba = nbaMapper.map(cdhResponse, "1234");

        verifyNbaIsNotNull(nba);
        assertThat(nba.getNextBestActions().getActions()).hasSize(1);
    }

    @Test
    void mapToNbaDto_when_status_isNOK() {

        CdhResponse cdhResponse = CdhResponse.builder()
                .status("OK")
                .containerList(List.of(NbaContainer.builder()
                        .status("NOK")
                        .rankedResults(List.of(RankedResult.builder()
                                .build()))
                        .build()))
                .build();

        NbaDto nba = nbaMapper.map(cdhResponse, "1234");

        assertThat(nba).isNotNull();
        assertThat(nba.getNextBestActions()).isNotNull();
        assertThat(nba.getNextBestActions().getActions()).isNullOrEmpty();
    }

    @Test
    void mapToNbaDto_when_MultipleSalesArgumentsPresent() {
        CdhResponse cdhResponse = CdhResponse.builder()
                .status("OK")
                .containerList(List.of(NbaContainer.builder()
                        .status("OK")
                        .rankedResults(List.of(RankedResult.builder()
                                .interactionId("INTERACTION-1234567-ID-12113")
                                .sellingArguments("2567809|198791")
                                .build()))
                        .build()))
                .build();

        NbaDto nba = nbaMapper.map(cdhResponse, "1234");

        verifyNbaIsNotNull(nba);
        assertThat(nba.getNextBestActions().getInteractionId()).isEqualTo("INTERACTION-1234567-ID-12113");
        List<SellingKey> sellingArguments = nba.getNextBestActions().getActions().getFirst().getSellingArguments();
        assertThat(sellingArguments).hasSize(2);
        assertThat(sellingArguments.get(0).getRank()).isEqualTo(1);
        assertThat(sellingArguments.get(0).getKey()).isEqualTo("2567809");
        assertThat(sellingArguments.get(1).getRank()).isEqualTo(2);
        assertThat(sellingArguments.get(1).getKey()).isEqualTo("198791");

    }

    @Test
    void mapToNbaDto_when_NoSalesArgumentPresent() {
        CdhResponse cdhResponse = CdhResponse.builder()
                .status("OK")
                .containerList(List.of(NbaContainer.builder()
                        .status("OK")
                        .rankedResults(List.of(RankedResult.builder()
                                .build()))
                        .build()))
                .build();

        NbaDto nba = nbaMapper.map(cdhResponse, "1234");

        verifyNbaIsNotNull(nba);
        List<SellingKey> sellingArguments = nba.getNextBestActions().getActions().getFirst().getSellingArguments();
        assertThat(sellingArguments).isEmpty();

    }

    @Test
    void mapToNbaDto_when_offers_with_product_status_isOK() {

        CdhResponse cdhResponse = CdhResponse.builder()
                .status("OK")
                .containerList(List.of(NbaContainer.builder()
                        .status("OK")
                        .rankedResults(List.of(getRankedResultWithProduct()))
                        .build()))
                .build();

        NbaDto nba = nbaMapper.map(cdhResponse, "1234");
        verifyNbaIsNotNull(nba);
        List<NextBestActionDto> nextBestOffers = nba.getNextBestActions().getActions();
        assertThat(nba.getNextBestActions().getInteractionId()).isEqualTo("INTERACTION-1234567-ID-12113");
        assertThat(nextBestOffers).hasSize(1);
        assertThat(nextBestOffers.getFirst().getSdataEncoded()).isNotNull();
        assertThat(nextBestOffers.getFirst().getSdataEncoded()).isEqualTo("XQAQAACJAAAAAAAAAABJINDxzH2M72y1xRx1xUDG3-psQm79UqU8zIBQRO-upIBnPGIQNQDC4oPrlXBc9DRzGbBrlpBo89H15abeHFaECafu5cOnUyNjlN2J6p8U-aBXERSFHAA=");
    }

    @Test
    void mapToNbaDto_when_offers_with_product_status_isOK_and_amountOfLimitedSims() {

        CdhResponse cdhResponse = CdhResponse.builder()
                .status("OK")
                .containerList(List.of(NbaContainer.builder()
                        .status("OK")
                        .rankedResults(List.of(getRankedResultWithProductAndAmountOfLimitedSims()))
                        .build()))
                .build();

        NbaDto nba = nbaMapper.map(cdhResponse, "1234");

        verifyNbaIsNotNull(nba);
        List<NextBestActionDto> nextBestOffers = nba.getNextBestActions().getActions();
        assertThat(nba.getNextBestActions().getInteractionId()).isEqualTo("INTERACTION-1234567-ID-12113");
        assertThat(nextBestOffers).hasSize(1);
        assertThat(nextBestOffers.getFirst().getSdataEncoded()).isEqualTo("XQAQAABIAAAAAAAAAABAqYnmbFEIbVbPAjgot3kLgpTdJoncaZCyv2z2I2KPvgX-Tartv67Magm1xpyzmY3O0_ucj5OdRQ9wVMgWF-7974t1lAAA");
    }

    private void verifyNbaIsNotNull(NbaDto nba) {
        assertThat(nba).isNotNull();
        assertThat(nba.getNextBestActions()).isNotNull();
        assertThat(nba.getNextBestActions().getActions()).isNotNull();
    }

    private void verifyRankedResultMapToNextBestOfferDto(NextBestActionDto dto) {
        assertThat(dto.getGroup()).isEqualTo("GROUP");
        assertThat(dto.getIssue()).isEqualTo("ISSUE");
        assertThat(dto.getName()).isEqualTo("NAME");
        assertThat(dto.getRank()).isEqualTo(1);
        assertThat(dto.getTreatment()).isEqualTo("TREATMENT");
        assertThat(dto.getTreatmentCategory()).isEqualTo("TREATMENT_CATEGORY");
        assertThat(dto.getProductType()).isEqualTo("PRODUCT_TYPE");
        assertThat(dto.getShortDescription()).isEqualTo("SHORT_DESCRIPTION");
        assertThat(dto.getCampaignId()).isEqualTo("campaign-promo-id");

        assertThat(dto.getSellingArguments()).hasSize(2);
        assertThat(dto.getSellingArguments().get(0).getKey()).isEqualTo("2567809");
        assertThat(dto.getSellingArguments().get(0).getRank()).isEqualTo(1);
        assertThat(dto.getSellingArguments().get(1).getKey()).isEqualTo("198791");
        assertThat(dto.getSellingArguments().get(1).getRank()).isEqualTo(2);
        assertThat(dto.getLocationId()).isEqualTo("9174161478313262218");
        assertThat(dto.getSdata()).isNull();
    }

    private RankedResult getRankedResult() {
        return RankedResult.builder()
                .channel("WEB")
                .group("GROUP")
                .issue("ISSUE")
                .interactionId("INTERACTION-1234567-ID-12113")
                .direction("DIRECTION")
                .name("NAME")
                .rank(1)
                .treatment("TREATMENT")
                .treatmentCategory("TREATMENT_CATEGORY")
                .productType("PRODUCT_TYPE")
                .shortDescription("SHORT_DESCRIPTION")
                .sellingArguments("2567809|198791")
                .campaignId("campaign-promo-id")
                .locationId("9174161478313262218")
                .build();
    }

    private RankedResult getRankedResultWithProduct() {
        return RankedResult.builder()
                .channel("WEB")
                .group("GROUP")
                .issue("ISSUE")
                .interactionId("INTERACTION-1234567-ID-12113")
                .direction("DIRECTION")
                .name("NAME")
                .rank(1)
                .treatment("TREATMENT")
                .treatmentCategory("TREATMENT_CATEGORY")
                .productType("PRODUCT_TYPE")
                .shortDescription("SHORT_DESCRIPTION")
                .sellingArguments("2567809|198791")
                .campaignId("campaign-promo-id")
                .product("[{\"movement\":\"same_spec\", \"omapiId\":\"INTL0008\",\"instanceId\":\"9174161473913266768\"}, {\"movement\":\"same_spec\",\"omapiId\":\"DITV1100\", \"instanceId\":\"9174161478813266768\"}]")
                .build();
    }

    private RankedResult getRankedResultWithProductAndAmountOfLimitedSims() {
        return RankedResult.builder()
                .channel("WEB")
                .group("GROUP")
                .issue("ISSUE")
                .interactionId("INTERACTION-1234567-ID-12113")
                .direction("DIRECTION")
                .name("NAME")
                .rank(1)
                .treatment("TREATMENT")
                .treatmentCategory("TREATMENT_CATEGORY")
                .productType("PRODUCT_TYPE")
                .shortDescription("SHORT_DESCRIPTION")
                .sellingArguments("2567809|198791")
                .campaignId("campaign-promo-id")
                .product("{\"offers\": [{\"id\": \"MobileProdct-123\",\"type\": \"Product\",\"amountOfLimitedSims\": 1,\"offers\": []}]}")
                .build();
    }

}
