package be.telenet.css.pegacdh.nba.cdh;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CdhResponse {

    @JsonProperty("Status")
    private String status;

    @JsonProperty("ContainerList")
    private List<NbaContainer> containerList;

}
