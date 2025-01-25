package chungbazi.chungbazi_be.domain.notification.dto;

import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


public class NotificationResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class responseDto{
        private Long notificationId;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class notificationDto {
        private Long notificationId;
        private boolean isRead;
        private String message;
        private NotificationType type;
        private LocalDateTime createdAt;
    }
}
