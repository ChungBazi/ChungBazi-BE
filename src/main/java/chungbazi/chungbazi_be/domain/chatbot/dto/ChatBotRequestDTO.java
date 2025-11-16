package chungbazi.chungbazi_be.domain.chatbot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ChatBotRequestDTO {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChatDto {

        @NotBlank(message = "메시지는 필수입니다")
        @Size(max = 1000, message = "메시지는 1000자를 초과할 수 없습니다")
        String message;
    }
}
