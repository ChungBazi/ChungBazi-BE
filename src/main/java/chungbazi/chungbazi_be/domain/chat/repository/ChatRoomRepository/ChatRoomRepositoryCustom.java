package chungbazi.chungbazi_be.domain.chat.repository.ChatRoomRepository;

import chungbazi.chungbazi_be.domain.chat.entity.ChatRoom;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepositoryCustom {

    List<ChatRoom> findActiveRoomsByUserId(Long userId);
}
