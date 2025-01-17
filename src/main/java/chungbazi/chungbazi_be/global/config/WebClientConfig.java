package chungbazi.chungbazi_be.global.config;

import io.netty.channel.ChannelOption;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    private String targetUrl = "https://www.youthcenter.go.kr/opi/youthPlcyList.do";

    @Bean
    public WebClient webclient() {

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 6000) //연걸 타임아웃 5000
                .responseTimeout(Duration.ofMillis(6000)) //응답 타임아웃 5000
                .followRedirect(true); // 리다이렉트 처리 활성화

        return WebClient.builder()
                .baseUrl(targetUrl)
                .defaultHeader(HttpHeaders.ACCEPT, "*/*")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
