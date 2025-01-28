package chungbazi.chungbazi_be.domain.notification.repository;


import chungbazi.chungbazi_be.domain.notification.entity.Notification;
import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;


import java.util.List;


public interface NotificationRepositoryCustom {

    void markAllAsRead(Long userId,NotificationType type);

    List<Notification> findNotificationsByUserIdAndNotificationType(Long userId, NotificationType type, Long cursor, int limit);

}
