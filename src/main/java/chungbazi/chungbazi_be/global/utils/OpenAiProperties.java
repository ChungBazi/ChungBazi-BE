package chungbazi.chungbazi_be.global.utils;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "openai")
@Getter
public class OpenAiProperties {
    private String apiKey;
    private String model;
    private String url;
}
