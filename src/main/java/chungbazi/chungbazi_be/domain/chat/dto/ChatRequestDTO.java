package chungbazi.chungbazi_be.domain.chat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChatRequestDTO {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MessageDto {

        @NotBlank(message = "메시지 내용은 필수입니다")
        @Size(max = 1000, message = "메시지는 1000자를 초과할 수 없습니다")
        private String content;

        private Long senderId;

        private Long receiverId;
    }
}
