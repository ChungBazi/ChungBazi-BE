package chungbazi.chungbazi_be.domain.chat.dto;

import chungbazi.chungbazi_be.domain.chat.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ChatResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class createChatRoomResponse{
        Long chatRoomId;
        Long senderId;
        Long receiverId;
        LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class messageResponse{
        Long id;
        Long senderId;
        Long chatRoomId;
        String content;
        boolean isRead;
        private LocalDateTime createdAt;
    }


}
