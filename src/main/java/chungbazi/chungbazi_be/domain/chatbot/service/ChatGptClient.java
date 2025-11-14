package chungbazi.chungbazi_be.domain.chatbot.service;

import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.GeneralException;
import chungbazi.chungbazi_be.global.utils.OpenAiProperties;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@RequiredArgsConstructor
public class ChatGptClient {
    private final OpenAiProperties openAiProperties;
    private final WebClient.Builder webClientBuilder;

    private static final Duration TIMEOUT = Duration.ofSeconds(30);

    @PostConstruct
    public void init() {
        this.webClient = webClientBuilder
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Content-Type", "application/json")
                .filter(logRequest()) // 로깅 필터
                .build();
    }

    private WebClient webClient;

    public Mono<String> askChatGpt(String userMessage, String systemPrompt){
        Map<String, Object> requestBody = Map.of(
                "model", openAiProperties.getModel(),
                "messages", List.of(
                        Map.of("role", "system", "content", systemPrompt),
                        Map.of("role", "user", "content", userMessage)
                ),
                "temperature", 0.7
        );

        return webClient.post()
                .uri("")
                .header("Authorization", "Bearer " + openAiProperties.getApiKey())
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(
                                        new GeneralException(ErrorStatus.OPENAI_API_ERROR)
                                ))
                )
                .bodyToMono(JsonNode.class)
                .timeout(TIMEOUT)
                .map(json -> {
                    JsonNode content = json.path("choices").path(0)
                            .path("message").path("content");
                    if (content.isMissingNode()) {
                        throw new GeneralException(ErrorStatus.OPENAI_INVALID_RESPONSE);
                    }
                    return content.asText();
                })                .onErrorMap(TimeoutException.class,
                        e -> new GeneralException(ErrorStatus.OPENAI_TIMEOUT))
                .doOnError(e -> log.error("OpenAI API call failed", e));
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            if (log.isDebugEnabled()) {
                ClientRequest filtered = ClientRequest.from(request)
                        .headers(headers -> headers.set("Authorization", "Bearer ***"))
                        .build();
                log.debug("Request: {} {}", filtered.method(), filtered.url());
            }
            return Mono.just(request);
        });
    }
}
