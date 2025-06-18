package chungbazi.chungbazi_be.domain.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ChatBotRequestDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class ChatDto {
        String message;
    }
}
