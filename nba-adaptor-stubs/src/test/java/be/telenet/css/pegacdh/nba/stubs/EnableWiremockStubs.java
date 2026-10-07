package be.telenet.css.pegacdh.nba.stubs;

import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigureWireMock(port = 0, stubs = "classpath*:/stubs/**/*.json")
public @interface EnableWiremockStubs {
}
