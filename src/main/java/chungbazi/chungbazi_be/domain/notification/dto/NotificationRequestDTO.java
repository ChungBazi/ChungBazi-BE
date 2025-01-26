package chungbazi.chungbazi_be.domain.notification.dto;


import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;
import lombok.Getter;

public class NotificationRequestDTO {

    @Getter
    public static class createDTO {
        private Long userId;
        private NotificationType type;
        private String message;
    }
}
