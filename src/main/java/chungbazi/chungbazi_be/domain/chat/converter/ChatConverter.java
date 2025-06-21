package chungbazi.chungbazi_be.domain.chat.converter;

import chungbazi.chungbazi_be.domain.chat.dto.ChatRequestDTO;
import chungbazi.chungbazi_be.domain.chat.dto.ChatResponseDTO;
import chungbazi.chungbazi_be.domain.chat.entity.ChatRoom;
import chungbazi.chungbazi_be.domain.chat.entity.Message;
import chungbazi.chungbazi_be.domain.user.entity.User;

public class ChatConverter {

    public static ChatResponseDTO.createChatRoomResponse toChatRoomDTO(ChatRoom chatRoom) {
        return ChatResponseDTO.createChatRoomResponse.builder()
                .chatRoomId(chatRoom.getId())
                .senderId(chatRoom.getSender().getId())
                .receiverId(chatRoom.getReceiver().getId())
                .createdAt(chatRoom.getCreatedAt())
                .build();
    }

    public static ChatResponseDTO.messageResponse from(Message message) {
        return ChatResponseDTO.messageResponse.builder()
                .id(message.getId())
                .chatRoomId(message.getChatRoom().getId())
                .senderId(message.getSender().getId())
                .content(message.getContent())
                .createdAt(message.getCreatedAt())
                .build();
    }

    public static ChatResponseDTO.chatDetailMessage toChatDetailMessageDTO(Message message, Long userId) {
        return ChatResponseDTO.chatDetailMessage.builder()
                .id(message.getId())
                .content(message.getContent())
                .senderId(message.getSender().getId())
                .isRead(message.isRead())
                .createdAt(message.getCreatedAt())
                .isMyMessage(message.getSender().getId().equals(userId))
                .build();
    }

}
