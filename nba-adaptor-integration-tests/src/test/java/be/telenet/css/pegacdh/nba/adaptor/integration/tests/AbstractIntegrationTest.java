package be.telenet.css.pegacdh.nba.adaptor.integration.tests;

import be.telenet.css.pegacdh.nba.NbaAdaptorApplication;
import be.telenet.css.pegacdh.nba.stubs.EnableWiremockStubs;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = NbaAdaptorApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
@EnableWiremockStubs
public abstract class AbstractIntegrationTest {

    protected static final String GET_NBA_V1_URL = "/v1/customer/{customerNumber}/next-best-actions";
    protected static final String CUSTOMER_NUMBER = "111111";
    protected static final String CUSTOMER_NUMBER_WITH_ONLY_PRODUCT = "333333";
    protected static final String CUSTOMER_NUMBER_WITH_SDATA_AND_PRODUCT = "222222";
    protected static final String VALUE_BRAND = "Telenet";
    protected static final String PARAM_CHANNEL = "channel";
    protected static final String VALUE_CHANNEL_WEB = "Web";
    protected static final String VALUE_CHANNEL_FIXIT_ADVISOR_TOOL = "FixitAdvisorTool";
    protected static final String PARAM_DIRECTION = "direction";
    protected static final String VALUE_DIRECTION = "Inbound";
    protected static final String BH_APP_CHANNEL ="BH_App";
    protected static final String CUSTOMER_NUMBER_BP_APP = "1065438296";

    @Autowired
    protected MockMvc mockMvc;

}
