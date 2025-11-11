package chungbazi.chungbazi_be.domain.notification.dto;

import chungbazi.chungbazi_be.domain.notification.entity.Notification;
import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;
import lombok.Getter;


public record FcmPushData(String message, NotificationType type, Long policyId, Long postId, Long chatId,
                          Long commentId) {
    public static FcmPushData from(Notification notification) {
        return new FcmPushData(
                notification.getMessage(),
                notification.getType(),
                notification.getPolicy() != null ? notification.getPolicy().getId() : null,
                notification.getPost() != null ? notification.getPost().getId() : null,
                notification.getChat() != null ? notification.getChat().getId() : null,
                notification.getComment() != null ? notification.getComment().getId() : null
        );
    }
}

