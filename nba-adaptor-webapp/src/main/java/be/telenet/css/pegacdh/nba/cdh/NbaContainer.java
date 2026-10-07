package be.telenet.css.pegacdh.nba.cdh;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class NbaContainer {

    @JsonProperty("Status")
    private String status;

    @JsonProperty("ContainerName")
    private String containerName;

    @JsonProperty("RankedResults")
    private List<RankedResult> rankedResults;
}
