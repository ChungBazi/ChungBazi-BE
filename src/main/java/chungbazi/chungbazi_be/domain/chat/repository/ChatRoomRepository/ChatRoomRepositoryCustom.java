package chungbazi.chungbazi_be.domain.chat.repository.ChatRoomRepository;

import chungbazi.chungbazi_be.domain.chat.entity.ChatRoom;

import java.util.List;

public interface ChatRoomRepositoryCustom {

    List<ChatRoom> findRoomsByUserIdAndIsActive(Long userId, Boolean active);

    List<ChatRoom> findBlockedChatRoomsByUserId(Long userId);

    List<ChatRoom> findActiveRoomsByUserId(Long userId);
}
