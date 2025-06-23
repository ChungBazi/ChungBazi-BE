package chungbazi.chungbazi_be.domain.chatbot.service;

import chungbazi.chungbazi_be.global.utils.OpenAiProperties;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class ChatGptClient {
    private final OpenAiProperties openAiProperties;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1/chat/completions")
            .defaultHeader("Content-Type", "application/json")
            .build();

    public String askChatGpt(String userMessage, String systemPrompt){
        Map<String, Object> requestBody = Map.of(
                "model", openAiProperties.getModel(),
                "messages", List.of(
                        Map.of("role","system", "content", systemPrompt),
                        Map.of("role", "user", "content", userMessage)
                ),
                "temperature", 0.7
        );

        return webClient.post()
                .uri("")
                .header("Authorization", "Bearer " + openAiProperties.getApiKey())
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> json.get("choices").get(0).get("message").get("content").asText())
                .block();
    }
}
