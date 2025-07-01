package chungbazi.chungbazi_be.domain.chat.converter;

import chungbazi.chungbazi_be.domain.chat.dto.ChatResponseDTO;
import chungbazi.chungbazi_be.domain.chat.entity.ChatRoom;
import chungbazi.chungbazi_be.domain.chat.entity.Message;
import chungbazi.chungbazi_be.domain.user.entity.User;

import java.util.Optional;

public class ChatConverter {

    public static ChatResponseDTO.createChatRoomResponse toChatRoomDTO(ChatRoom chatRoom,Long senderId, Long authorId) {
        return ChatResponseDTO.createChatRoomResponse.builder()
                .chatRoomId(chatRoom.getId())
                .senderId(senderId)
                .receiverId(authorId)
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

    public static ChatResponseDTO.chatRoomListResponse toChatRoomListResponse(ChatRoom chatRoom, Message lastMessage, User receiver) {
        return ChatResponseDTO.chatRoomListResponse.builder()
                .chatRoomId(chatRoom.getId())
                .postTile(chatRoom.getPost().getTitle())
                .lastMessage(lastMessage != null ? lastMessage.getContent() : "")
                .lastMessageTime(lastMessage != null ? lastMessage.getCreatedAt() : null)
                .receiverName(receiver.getName())
                .receiverId(receiver.getId())
                .isRead(lastMessage != null ? lastMessage.isRead() : false)
                .build();
    }

}
