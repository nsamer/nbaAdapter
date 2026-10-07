package be.telenet.css.pegacdh.nba.cdh;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.standalone.JsonFileMappingsSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.contract.wiremock.file.ResourcesFileSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;

@Slf4j
abstract class AbstractCdhMockServer {

    private static final String MAPPINGS_PATH = "stubs/mappings";
    private final WireMockServer wireMockServer;

    protected AbstractCdhMockServer() throws IOException {
        wireMockServer = createWiremockServer();
        wireMockServer.start();
        log.info("Wire mock server started on port: {}", wireMockServer.port());
    }

    private static WireMockServer createWiremockServer() throws IOException {
        var fileSource = buildMappingsFileSource();
        return new WireMockServer(WireMockConfiguration
                .options()
                .dynamicPort()
                .mappingSource(new JsonFileMappingsSource(fileSource)));
    }

    private static FileSource buildMappingsFileSource() throws IOException {
        var urlResource = new UrlResource(new ClassPathResource(MAPPINGS_PATH).getURI());
        FileSource resourcesFileSource = new ResourcesFileSource(urlResource);
        if (!urlResource.isFile()) {
            resourcesFileSource = resourcesFileSource.child(MAPPINGS_PATH);
        }
        return resourcesFileSource;
    }

    public int getWireMockServerPort() {
        return wireMockServer.port();
    }

}
