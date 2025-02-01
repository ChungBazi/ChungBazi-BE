package chungbazi.chungbazi_be.domain.notification.repository;

import chungbazi.chungbazi_be.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>,NotificationRepositoryCustom {
    List<Notification> findAllByUserId(Long userId);
}
