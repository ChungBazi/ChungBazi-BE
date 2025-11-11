package chungbazi.chungbazi_be.domain.notification.converter;

import chungbazi.chungbazi_be.domain.chat.entity.Message;
import chungbazi.chungbazi_be.domain.community.entity.Comment;
import chungbazi.chungbazi_be.domain.community.entity.Post;
import chungbazi.chungbazi_be.domain.notification.dto.NotificationRequest;
import chungbazi.chungbazi_be.domain.notification.entity.Notification;
import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;
import chungbazi.chungbazi_be.domain.policy.entity.Policy;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotificationHandler;

public class NotificationFactory {
    public static Notification from(NotificationRequest request) {
        switch (request.getType()) {
            case POLICY_ALARM:
                return createPolicyNotification(
                        request.getUser(),
                        request.getMessage(),
                        request.getPolicy()
                );
            case COMMUNITY_ALARM:
                return createCommunityNotification(
                        request.getUser(),
                        request.getMessage(),
                        request.getPost(),
                        request.getComment()
                );
            case CHAT_ALARM:
                return createChatNotification(
                        request.getUser(),
                        request.getMessage(),
                        request.getChat()
                );
            case REWARD_ALARM:
                return createRewardNotification(
                        request.getUser(),
                        request.getMessage()
                );
            default:
                throw new NotificationHandler(ErrorStatus.INVALID_NOTIFICATION_TYPE);
        }
    }

    public static Notification createPolicyNotification(User user, String message, Policy policy) {
        return Notification.builder()
                .user(user)
                .type(NotificationType.POLICY_ALARM)
                .message(message)
                .policy(policy)
                .isRead(false)
                .build();
    }

    public static Notification createCommunityNotification(User user, String message,
                                                           Post post, Comment comment) {
        return Notification.builder()
                .user(user)
                .type(NotificationType.COMMUNITY_ALARM)
                .message(message)
                .post(post)
                .comment(comment)
                .isRead(false)
                .build();
    }

    public static Notification createChatNotification(User user, String message, Message chat) {
        return Notification.builder()
                .user(user)
                .type(NotificationType.CHAT_ALARM)
                .message(message)
                .chat(chat)
                .isRead(false)
                .build();
    }

    public static Notification createRewardNotification(User user, String message) {
        return Notification.builder()
                .user(user)
                .type(NotificationType.REWARD_ALARM)
                .message(message)
                .isRead(false)
                .build();
    }
}
