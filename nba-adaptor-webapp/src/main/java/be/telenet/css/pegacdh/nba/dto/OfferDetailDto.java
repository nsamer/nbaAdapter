package be.telenet.css.pegacdh.nba.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfferDetailDto {

    private String name;
    private String treatment;
    private List<TreatmentFragmentDto> treatmentFragments;
}
