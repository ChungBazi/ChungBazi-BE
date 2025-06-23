package chungbazi.chungbazi_be.domain.chat.repository.MessageRepository;

import chungbazi.chungbazi_be.domain.chat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>,MessageRepositoryCustom {
    @Query("SELECT m FROM Message m " +
            "WHERE m.chatRoom.id = :chatRoomId " +
            "ORDER BY m.createdAt DESC " +
            "LIMIT 1")
    Optional<Message> findLastMessageByChatRoomId(Long chatRoomId);
}
