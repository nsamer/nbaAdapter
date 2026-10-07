package be.telenet.css.pegacdh.nba.mapper;

import be.telenet.css.pegacdh.nba.cdh.CdhResponse;
import be.telenet.css.pegacdh.nba.cdh.NbaContainer;
import be.telenet.css.pegacdh.nba.cdh.RankedResult;
import be.telenet.css.pegacdh.nba.cdh.TreatmentFragment;
import be.telenet.css.pegacdh.nba.dto.NbaDto;
import be.telenet.css.pegacdh.nba.dto.NextBestActionDto;
import be.telenet.css.pegacdh.nba.dto.NextBestActionForCustomerDto;
import be.telenet.css.pegacdh.nba.dto.OfferDetailDto;
import be.telenet.css.pegacdh.nba.dto.SellingKey;
import be.telenet.css.pegacdh.nba.dto.TreatmentFragmentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.msgpack.jackson.dataformat.MessagePackMapper;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.LZMAOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Slf4j
public abstract class AbstractNbaMapper {

    public static final String STATUS_OK = "OK";
    public static final String SALES_ARGUMENT_SPLIT_CHARACTER = "\\|";
    public static final int INITIAL_SALES_ARGUMENT_RANK = 1;

    public List<NextBestActionDto> getNextBestActions(CdhResponse cdhResponse) {
        return STATUS_OK.equalsIgnoreCase(cdhResponse.getStatus())
                ? emptyIfNull(cdhResponse.getContainerList())
                .stream()
                .filter(e -> isBlank(e.getStatus()) || STATUS_OK.equals(e.getStatus()))
                .flatMap(e -> emptyIfNull(e.getRankedResults()).stream())
                .map(this::mapToNextBestOfferDto)
                .toList()
                : List.of();
    }

    public NextBestActionDto mapToNextBestOfferDto(RankedResult rankedResult) {
        return NextBestActionDto.builder()
                .group(rankedResult.getGroup())
                .issue(rankedResult.getIssue())
                .name(rankedResult.getName())
                .rank(rankedResult.getRank())
                .treatment(rankedResult.getTreatment())
                .treatmentCategory(rankedResult.getTreatmentCategory())
                .productType(rankedResult.getProductType())
                .shortDescription(rankedResult.getShortDescription())
                .campaignId(rankedResult.getCampaignId())
                .sellingArguments(getSellingKeys(rankedResult.getSellingArguments()))
                .sdata(getSdata(rankedResult.getProduct()))
                .sdataEncoded(getSdataEncoded(rankedResult))
                .locationId(rankedResult.getLocationId())
                .build();
    }

    public NbaDto map(List<NextBestActionDto> nextBestActionDtos, NbaContainer nbaContainer, String customerNumber) {
        var rankedResult = getFirstRankedResult(nbaContainer);
        return  NbaDto.builder()
                .nextBestActions(NextBestActionForCustomerDto
                        .builder()
                        .interactionId(rankedResult.getInteractionId())
                        .customerNumber(customerNumber)
                        .channel(rankedResult.getChannel())
                        .containerName(nbaContainer.getContainerName())
                        .actions(nextBestActionDtos)
                        .offerDetails(getOfferDetails(nbaContainer))
                        .build())
                .build();
    }

    private List<OfferDetailDto> getOfferDetails(NbaContainer nbaContainer) {
        return nbaContainer.getRankedResults().stream()
                .filter(rs -> isNotEmpty(rs.getOfferDetails()))
                .flatMap(rs -> rs.getOfferDetails().stream())
                .map(offerDetail -> OfferDetailDto.builder()
                        .name(offerDetail.getName())
                        .treatment(offerDetail.getTreatment())
                        .treatmentFragments(getTreatmentFragments(offerDetail.getTreatmentFragments()))
                        .build())
                .toList();
    }

    private List<TreatmentFragmentDto> getTreatmentFragments(List<TreatmentFragment> treatmentFragments) {
        return emptyIfNull(treatmentFragments).stream()
                .map(treatmentFragment -> TreatmentFragmentDto.builder()
                        .treatmentFragment(treatmentFragment.getTreatmentFragment())
                        .containerFragment(treatmentFragment.getContainerFragment())
                        .experienceTemplate(treatmentFragment.getExperienceTemplate())
                        .contentFragment(treatmentFragment.getContentFragment())
                        .build())
                .toList();
    }

    private List<SellingKey> getSellingKeys(String keys) {
        if(isNotEmpty(keys)) {
            String[] nboSalesArguments = StringUtils.split(keys, SALES_ARGUMENT_SPLIT_CHARACTER);
            var rankCounter = new AtomicInteger(INITIAL_SALES_ARGUMENT_RANK);
            return isNull(nboSalesArguments) ? List.of() :Arrays.stream(nboSalesArguments)
                    .map(salesArgument->mapToKeyDto(salesArgument, rankCounter.getAndIncrement()))
                    .toList();
        } else {
            return List.of();
        }
    }

    private SellingKey mapToKeyDto(String salesArgument, int rank) {
        return SellingKey.builder()
                .key(salesArgument.trim())
                .rank(rank)
                .build();
    }

    private String getSdataEncoded(RankedResult rankedResult) {
        if (isNotEmpty(rankedResult.getProduct())) {
            String sdata = rankedResult.getProduct();
            try {
                ObjectMapper objectMapper = new MessagePackMapper();
                Object sdataJson = objectMapper.readValue(sdata,Object.class);
                byte[] packedBytes = objectMapper.writeValueAsBytes(sdataJson);

                ByteArrayOutputStream out = new ByteArrayOutputStream(packedBytes.length);
                LZMA2Options options = new LZMA2Options();
                options.setDictSize(LZMA2Options.DICT_SIZE_MIN);
                LZMAOutputStream lzmaOut = new LZMAOutputStream(out, options, packedBytes.length);

                lzmaOut.write(packedBytes);
                lzmaOut.close();

                return Base64.getUrlEncoder().encodeToString(out.toByteArray());
            } catch (IOException e) {
                log.warn("sdata encoding error: {}", e.getMessage());
                return null;
            }
        }
        return null;
    }

    private Object getSdata(String productId) {
        var gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(productId, Object.class);
    }

    public NbaContainer getFirstValidContainer(CdhResponse cdhResponse) {
       return nonNull(cdhResponse) ? emptyIfNull(cdhResponse.getContainerList()).stream()
                .filter(e -> isBlank(e.getStatus()) || STATUS_OK.equals(e.getStatus()))
                .findFirst()
                .orElse(NbaContainer.builder().build())
               : NbaContainer.builder().build();
    }

    private RankedResult getFirstRankedResult(NbaContainer nbaContainer) {
        return emptyIfNull(nbaContainer.getRankedResults()).stream()
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(RankedResult.builder().build());
    }

    public NbaDto getEmptyNbaResponse() {
        return NbaDto.builder()
                .nextBestActions(NextBestActionForCustomerDto.builder().build())
                .build();
    }

}
