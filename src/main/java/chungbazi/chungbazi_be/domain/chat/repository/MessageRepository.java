package chungbazi.chungbazi_be.domain.chat.repository;

import chungbazi.chungbazi_be.domain.chat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
