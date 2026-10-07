package be.telenet.css.pegacdh.nba.cdh.nbaclient;

import be.telenet.css.pegacdh.nba.cdh.CdhRequest;
import be.telenet.css.pegacdh.nba.cdh.CdhResponse;

public interface NextBestActionService {

    CdhResponse getNextBestAction(CdhRequest request, String header);

    String getUrl();
}
