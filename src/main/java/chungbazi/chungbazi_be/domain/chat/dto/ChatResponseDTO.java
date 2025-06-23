package chungbazi.chungbazi_be.domain.chat.dto;

import chungbazi.chungbazi_be.domain.chat.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

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
        Long receiverId;
        Long chatRoomId;
        String content;
        boolean isRead;
        LocalDateTime createdAt;
    }

    @Getter @Builder @AllArgsConstructor
    public static class chatDetailMessage {
        Long id;
        Long senderId;
        String content;
        boolean isRead;
        LocalDateTime createdAt;
        boolean isMyMessage;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class chatRoomResponse{
        Long chatRoomId;
        String postTitle;
        String receiverName;
        Long receiverId;
        List<chatDetailMessage> messageList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class chatRoomListResponse{
        Long chatRoomId;
        String postTile;
        String receiverName;
        Long receiverId;
        String lastMessage;
        LocalDateTime lastMessageTime;
        boolean isRead;
    }


}
