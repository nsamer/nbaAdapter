package be.telenet.css.pegacdh.nba.cdh;

import be.telenet.css.pegacdh.nba.common.config.CdhHeaderProperties;
import be.telenet.css.pegacdh.nba.dto.NbaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CdhRequestBuilder {

    private final CdhHeaderProperties cdhHeaderProperties;

    public CdhRequest createRequest(NbaRequest nbaRequest) {
        return CdhRequest.builder()
                .channel(nbaRequest.getChannel())
                .containerName(cdhHeaderProperties.getContainerName())
                .subjectId(nbaRequest.getCustomerNumber())
                .contextName(nbaRequest.getContextName())
                .direction(nbaRequest.getCallDirection())
                .build();
    }

}
