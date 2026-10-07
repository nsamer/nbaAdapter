package be.telenet.css.pegacdh.nba.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreatmentFragmentDto {

    private String treatmentFragment;
    private String containerFragment;
    private String contentFragment;
    private String experienceTemplate;
}
