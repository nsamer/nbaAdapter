package be.telenet.css.pegacdh.nba.common.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cdh.header")
@Data
@ToString
public class CdhHeaderProperties {

    private  String environment;
    private  String applicationName;
    private  String organizationCompany;
    private  int organizationTenantId;
    private  String userInfoIdentityType;
    private String containerName;

}
