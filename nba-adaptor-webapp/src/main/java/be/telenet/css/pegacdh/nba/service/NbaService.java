package be.telenet.css.pegacdh.nba.service;

import be.telenet.css.pegacdh.nba.cdh.CdhRequestBuilder;
import be.telenet.css.pegacdh.nba.cdh.integrationheader.IntegrationHeaderService;
import be.telenet.css.pegacdh.nba.cdh.nbaclient.NextBestActionService;
import be.telenet.css.pegacdh.nba.dto.NbaDto;
import be.telenet.css.pegacdh.nba.dto.NbaRequest;
import be.telenet.css.pegacdh.nba.mapper.NbaMapper;
import org.springframework.stereotype.Service;

@Service
public class NbaService {

    private final NextBestActionService nextBestActionService;
    private final IntegrationHeaderService integrationHeaderService;
    private final CdhRequestBuilder cdhRequestBuilder;
    private final NbaMapper nbaMapper;

    public NbaService(NextBestActionService nextBestActionService, IntegrationHeaderService integrationHeaderService, CdhRequestBuilder cdhRequestBuilder, NbaMapper nbaMapper) {
        this.nextBestActionService = nextBestActionService;
        this.integrationHeaderService = integrationHeaderService;
        this.cdhRequestBuilder = cdhRequestBuilder;
        this.nbaMapper = nbaMapper;
    }

    public NbaDto getNextBestAction(NbaRequest nbaRequest) {
        var cdhRequest = cdhRequestBuilder.createRequest(nbaRequest);
        var header = integrationHeaderService.getIntegrationHeader(cdhRequest).toJsonString();
        var cdhResponse = nextBestActionService.getNextBestAction(cdhRequest, header);
        return nbaMapper.map(cdhResponse, nbaRequest.getCustomerNumber());
    }

}
