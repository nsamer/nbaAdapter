package be.telenet.css.pegacdh.nba.cdh;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class CdhRequest {
    @JsonProperty("ContainerName")
    private String containerName;
    @JsonProperty("Channel")
    private String channel;
    @JsonProperty("SubjectID")
    private String subjectId;
    @JsonProperty("ContextName")
    private String contextName;
    @JsonProperty("Direction")
    private String direction;
}
