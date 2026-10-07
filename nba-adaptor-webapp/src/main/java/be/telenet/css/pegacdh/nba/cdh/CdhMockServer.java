package be.telenet.css.pegacdh.nba.cdh;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@ConditionalOnProperty(value = "cdh.source", havingValue = "MOCK")
public class CdhMockServer extends AbstractCdhMockServer {

    public CdhMockServer() throws IOException {
        super();
    }

}
