package chungbazi.chungbazi_be.domain.notification.entity;

import chungbazi.chungbazi_be.domain.chat.entity.Message;
import chungbazi.chungbazi_be.domain.community.entity.Comment;
import chungbazi.chungbazi_be.domain.community.entity.Post;
import chungbazi.chungbazi_be.global.utils.TimeFormatter;
import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;
import chungbazi.chungbazi_be.domain.policy.entity.Policy;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Column(nullable = false)
    private boolean isRead;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Column(length = 1000)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 커뮤니티 알림인 경우 (선택적)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    // 정책 알림인 경우 (선택적)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id")
    private Policy policy;

    //쪽지 알림인 경우
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Message chat;


    public void markAsRead() {
        this.isRead = true;
    }

    public String getFormattedCreatedAt(){
        return TimeFormatter.formatCreatedAt(this.getCreatedAt());
    }

    public Long getRelatedEntityId() {
        switch (type) {
            case POLICY_ALARM:
                return policy != null ? policy.getId() : null;
            case COMMUNITY_ALARM:
                return post != null ? post.getId() : null;
            case CHAT_ALARM:
                return chat != null ? chat.getId() : null;
            default:
                return null;
        }
    }
}
