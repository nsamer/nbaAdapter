package be.telenet.css.pegacdh.nba.cdh.integrationheader;

import be.telenet.css.lib.integrationheader.v2.IntegrationHeader;

public class IntegrationHeaderService {

    private final IntegrationHeaderMapper integrationHeaderMapper;

    private final IntegrationHeaderProperties integrationHeaderProperties;

    public IntegrationHeaderService(IntegrationHeaderProperties integrationHeaderProperties) {
        this.integrationHeaderMapper = new IntegrationHeaderMapper();
        this.integrationHeaderProperties = integrationHeaderProperties;
    }

    public IntegrationHeader getIntegrationHeader(Object feedbackBody) {
        return integrationHeaderMapper.toDomain(feedbackBody, integrationHeaderProperties);
    }

}

