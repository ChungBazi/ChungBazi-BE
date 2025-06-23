package chungbazi.chungbazi_be.domain.chat.repository.MessageRepository;

import chungbazi.chungbazi_be.domain.chat.entity.Message;

import java.util.List;

public interface MessageRepositoryCustom {
    List<Message> findMessagesByChatRoomId(Long chatRoomId,Long cursor, int limit);

    long markAllAsRead(Long chatRoomId, Long userId);
}
