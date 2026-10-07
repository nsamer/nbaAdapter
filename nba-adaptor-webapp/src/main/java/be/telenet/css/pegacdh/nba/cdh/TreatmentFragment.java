package be.telenet.css.pegacdh.nba.cdh;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreatmentFragment {

    @JsonProperty("TreatmentFragment")
    private String treatmentFragment;

    @JsonProperty("ContainerFragment")
    private String containerFragment;

    @JsonProperty("ContentFragment")
    private String contentFragment;

    @JsonProperty("ExperienceTemplate")
    private String experienceTemplate;
}
