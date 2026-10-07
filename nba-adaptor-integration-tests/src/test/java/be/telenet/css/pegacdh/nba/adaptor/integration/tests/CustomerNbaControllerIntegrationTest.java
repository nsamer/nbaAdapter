package be.telenet.css.pegacdh.nba.adaptor.integration.tests;

import be.telenet.css.pegacdh.nba.mapper.NbaRequestMapper;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerNbaControllerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void should_return_ok_if_request_from_fixit_channel_without_optional_request_parameters() throws Exception {
        mockMvc.perform(
                get(GET_NBA_V1_URL, CUSTOMER_NUMBER)
                        .queryParam(PARAM_CHANNEL, VALUE_CHANNEL_FIXIT_ADVISOR_TOOL)
                        .header(NbaRequestMapper.HEADER_BRAND, VALUE_BRAND))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nextBestActions.customerNumber").value("111111"))
            .andExpect(jsonPath("$.nextBestActions.customerNumber").exists());
    }

    @Test
    void should_return_ok_if_request_from_fixit_channel_with_optional_request_parameters() throws Exception {
        mockMvc.perform(
                        get(GET_NBA_V1_URL, CUSTOMER_NUMBER)
                                .queryParam(PARAM_CHANNEL, VALUE_CHANNEL_FIXIT_ADVISOR_TOOL)
                                .queryParam(PARAM_DIRECTION, VALUE_DIRECTION)
                                .header(NbaRequestMapper.HEADER_BRAND, VALUE_BRAND))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nextBestActions.customerNumber").value("111111"));
    }

    @Test
    void should_return_ok_if_request_from_BH_App_channel_with_optional_request_parameters() throws Exception {
        mockMvc.perform(
                        get(GET_NBA_V1_URL, CUSTOMER_NUMBER_BP_APP)
                                .queryParam(PARAM_CHANNEL, BH_APP_CHANNEL)
                                .queryParam(PARAM_DIRECTION, VALUE_DIRECTION)
                                .header(NbaRequestMapper.HEADER_BRAND, VALUE_BRAND))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nextBestActions.customerNumber").value("1065438296"))
                .andExpect(jsonPath("$.nextBestActions.interactionId").value("-4180209308855548242"))
                .andExpect(jsonPath("$.nextBestActions.channel").value("BH_App"))
                .andExpect(jsonPath("$.nextBestActions.containerName").value("GetNBA"))

                // Action 0
                .andExpect(jsonPath("$.nextBestActions.actions[0].rank").value(1))
                .andExpect(jsonPath("$.nextBestActions.actions[0].group").value("Product"))
                .andExpect(jsonPath("$.nextBestActions.actions[0].issue").value("ProductEngagement"))
                .andExpect(jsonPath("$.nextBestActions.actions[0].name").value("PEPR_Safesurf"))
                .andExpect(jsonPath("$.nextBestActions.actions[0].treatment").value("SecurityOption_BH_App_SecurityCheck"))
                .andExpect(jsonPath("$.nextBestActions.actions[0].treatmentCategory").value("SecurityCheck"))
                .andExpect(jsonPath("$.nextBestActions.actions[0].campaignId").value(""))
                .andExpect(jsonPath("$.nextBestActions.actions[0].productType").value("SecurityOption"))
                .andExpect(jsonPath("$.nextBestActions.actions[0].shortDescription").value("Activate Safesurf or Safesurf B2B for INT customers"))
                .andExpect(jsonPath("$.nextBestActions.actions[0].sellingArguments[0].rank").value(1))
                .andExpect(jsonPath("$.nextBestActions.actions[0].sellingArguments[0].key").value("SAFE_1"))
                .andExpect(jsonPath("$.nextBestActions.actions[0].sellingArguments[1].rank").value(2))
                .andExpect(jsonPath("$.nextBestActions.actions[0].sellingArguments[1].key").value("SAFE_2"))
                .andExpect(jsonPath("$.nextBestActions.actions[0].sellingArguments[2].rank").value(3))
                .andExpect(jsonPath("$.nextBestActions.actions[0].sellingArguments[2].key").value("SAFE_3"))
                .andExpect(jsonPath("$.nextBestActions.actions[0].sdata.offers[0].id").value("OIKO0102"))
                .andExpect(jsonPath("$.nextBestActions.actions[0].sdata.offers[0].type").value("Product"))
                .andExpect(jsonPath("$.nextBestActions.actions[0].sdata.offers[0].offers").isArray())
                .andExpect(jsonPath("$.nextBestActions.actions[1].sdata.offers[1].id").doesNotExist()) // Only one offer in sdata.offers
                .andExpect(jsonPath("$.nextBestActions.actions[0].sdataEncoded").value("XQAQAABNAAAAAAAAAABAqYnmbFEIbVbh_xyot3CAOYkYz812N1X4sN83CFFGkV5WZjop-lrxA2T1wAAA"))

                // Action 1
                .andExpect(jsonPath("$.nextBestActions.actions[1].rank").value(2))
                .andExpect(jsonPath("$.nextBestActions.actions[1].group").value("Change"))
                .andExpect(jsonPath("$.nextBestActions.actions[1].issue").value("Retention"))
                .andExpect(jsonPath("$.nextBestActions.actions[1].name").value("RECH_ONEupfor1_Iconic_Down"))
                .andExpect(jsonPath("$.nextBestActions.actions[1].treatment").value("InternetOther_BH_App_ProductCheck"))
                .andExpect(jsonPath("$.nextBestActions.actions[1].treatmentCategory").value("ProductCheck"))
                .andExpect(jsonPath("$.nextBestActions.actions[1].campaignId").value(""))
                .andExpect(jsonPath("$.nextBestActions.actions[1].productType").value("One"))
                .andExpect(jsonPath("$.nextBestActions.actions[1].shortDescription").value("Offer ONE Up for 1 incl TV for high at risk customers"))
                .andExpect(jsonPath("$.nextBestActions.actions[1].sellingArguments[0].rank").value(1))
                .andExpect(jsonPath("$.nextBestActions.actions[1].sellingArguments[0].key").value("INT2One_1"))
                .andExpect(jsonPath("$.nextBestActions.actions[1].sellingArguments[1].rank").value(2))
                .andExpect(jsonPath("$.nextBestActions.actions[1].sellingArguments[1].key").value("INT2One_2"))
                .andExpect(jsonPath("$.nextBestActions.actions[1].sellingArguments[2].rank").value(3))
                .andExpect(jsonPath("$.nextBestActions.actions[1].sellingArguments[2].key").value("INT2One_3"))
                .andExpect(jsonPath("$.nextBestActions.actions[1].sellingArguments[3].rank").value(4))
                .andExpect(jsonPath("$.nextBestActions.actions[1].sellingArguments[3].key").value("INT2One_4"))
                .andExpect(jsonPath("$.nextBestActions.actions[1].sellingArguments[4].rank").value(5))
                .andExpect(jsonPath("$.nextBestActions.actions[1].sellingArguments[4].key").value("INT2One_5"))
                .andExpect(jsonPath("$.nextBestActions.actions[1].sdata.offers[0].id").value("C5FU0006"))
                .andExpect(jsonPath("$.nextBestActions.actions[1].sdata.offers[0].type").value("BUNDLE"))
                .andExpect(jsonPath("$.nextBestActions.actions[1].sdata.offers[0].offers[0].id").value("TEOS0001"))
                .andExpect(jsonPath("$.nextBestActions.actions[1].sdata.offers[0].offers[0].type").value("IDTVLINE"))
                .andExpect(jsonPath("$.nextBestActions.actions[1].sdata.offers[0].offers[0].offers").isArray())
                .andExpect(jsonPath("$.nextBestActions.actions[1].sdata.offers[0].amountOfLimitedSims").value(0.0))
                .andExpect(jsonPath("$.nextBestActions.actions[1].sdataEncoded").value("XQAQAABiAAAAAAAAAABAqYnmbFEIbVbPAjgot3B5ANJB1QjtM4f8shZUlyTCjUJ7foWLAIZnncD_kj2ibwOCVnnQPVswCAl3qKBBz6kbbO_BNZF8ZifUW48doZOWfAAA"))

                // offerDetails
                .andExpect(jsonPath("$.nextBestActions.offerDetails").isArray());
        }

    }

