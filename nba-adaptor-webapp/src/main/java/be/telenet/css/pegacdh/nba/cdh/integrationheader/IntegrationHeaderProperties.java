package be.telenet.css.pegacdh.nba.cdh.integrationheader;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("telenet.integration-header")
@Data
@ToString
@Validated
public class IntegrationHeaderProperties {

    @NotBlank
    private String environment;
    @NotBlank
    private String applicationName;

}

