package chungbazi.chungbazi_be.domain.chat.repository;

import chungbazi.chungbazi_be.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
