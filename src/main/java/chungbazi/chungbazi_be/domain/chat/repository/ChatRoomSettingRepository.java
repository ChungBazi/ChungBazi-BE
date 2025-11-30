package chungbazi.chungbazi_be.domain.chat.repository;

import chungbazi.chungbazi_be.domain.chat.entity.ChatRoomSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomSettingRepository extends JpaRepository<ChatRoomSetting, Long> {

    Optional<ChatRoomSetting> findByUserIdAndChatRoomId(Long userId, Long chatRoomId);
}
