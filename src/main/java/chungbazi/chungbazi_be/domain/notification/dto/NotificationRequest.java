package chungbazi.chungbazi_be.domain.notification.dto;

import chungbazi.chungbazi_be.domain.chat.entity.Message;
import chungbazi.chungbazi_be.domain.community.entity.Comment;
import chungbazi.chungbazi_be.domain.community.entity.Post;
import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;
import chungbazi.chungbazi_be.domain.policy.entity.Policy;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotificationHandler;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    @NotNull
    private User user;

    @NotNull
    private NotificationType type;

    @NotBlank
    private String message;

    private Post post;

    private Policy policy;

    private Message chat;

    private Comment comment;

    public void validate() {
        switch (type) {
            case POLICY_ALARM:
                if (policy == null) {
                    throw new NotificationHandler(ErrorStatus.POLICY_ALARM_CHAT_NULL);
                }
                break;
            case COMMUNITY_ALARM:
                if (post == null || comment == null) {
                    throw new NotificationHandler(ErrorStatus.COMMUNITY_ALARM_POST_OR_COMMENT_NULL);
                }
                break;
            case CHAT_ALARM:
                if (chat == null) {
                    throw new NotificationHandler(ErrorStatus.CHAT_ALARM_CHAT_NULL);
                }
                break;
        }
    }
}
