package chungbazi.chungbazi_be.domain.chat.converter;

import chungbazi.chungbazi_be.domain.chat.dto.ChatResponseDTO;
import chungbazi.chungbazi_be.domain.chat.entity.ChatRoom;
import chungbazi.chungbazi_be.domain.chat.entity.Message;

import java.util.Optional;

public class ChatConverter {

    public static ChatResponseDTO.createChatRoomResponse toChatRoomDTO(ChatRoom chatRoom) {
        return ChatResponseDTO.createChatRoomResponse.builder()
                .chatRoomId(chatRoom.getId())
                .senderId(chatRoom.getSender().getId())
                .receiverId(chatRoom.getReceiver().getId())
                .createdAt(chatRoom.getCreatedAt())
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

    public static ChatResponseDTO.chatRoomListResponse toChatRoomListResponse(ChatRoom chatRoom, Message lastMessage) {
        return ChatResponseDTO.chatRoomListResponse.builder()
                .chatRoomId(chatRoom.getId())
                .postTile(chatRoom.getPost().getTitle())
                .lastMessage(lastMessage != null ? lastMessage.getContent() : "")
                .lastMessageTime(lastMessage != null ? lastMessage.getCreatedAt() : null)
                .receiverName(chatRoom.getReceiver().getName())
                .isRead(lastMessage != null ? lastMessage.isRead() : false)
                .build();
    }

}
