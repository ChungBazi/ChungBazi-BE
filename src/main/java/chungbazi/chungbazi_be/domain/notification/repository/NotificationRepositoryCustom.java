package chungbazi.chungbazi_be.domain.notification.repository;


import chungbazi.chungbazi_be.domain.notification.dto.NotificationResponseDTO;
import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;


import java.util.List;


public interface NotificationRepositoryCustom {

    void markAllAsRead(Long userId,NotificationType type);

    List<NotificationResponseDTO.notificationDto> findNotificationsByUserIdAndNotificationTypeDto(Long userId, NotificationType type, Long cursor, int limit);

}
