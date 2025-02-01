package chungbazi.chungbazi_be.domain.notification.converter;

import chungbazi.chungbazi_be.domain.notification.dto.NotificationResponseDTO;
import chungbazi.chungbazi_be.domain.notification.entity.Notification;

public class NotificationConverter {

    public static NotificationResponseDTO.notificationDto toNotificationDto(Notification notification){
        return NotificationResponseDTO.notificationDto.builder()
                .notificationId(notification.getId())
                .isRead(notification.isRead())
                .message(notification.getMessage())
                .type(notification.getType())
                .build();
    }
}
