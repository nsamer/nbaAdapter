package be.telenet.css.pegacdh.nba.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer objectMapperBuilderCustomizer() {
        return objectMapper -> objectMapper.featuresToDisable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
    }

}
