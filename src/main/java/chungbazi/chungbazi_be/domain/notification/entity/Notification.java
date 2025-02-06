package chungbazi.chungbazi_be.domain.notification.entity;

import chungbazi.chungbazi_be.domain.community.utils.TimeFormatter;
import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.global.entity.BaseTimeEntity;
import com.google.firebase.database.annotations.NotNull;
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
    @NotNull
    private NotificationType type;

    @Column(length = 1000)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //댓글이랑 연관관계

    //장바구니랑 연관관계

    // Notification 엔티티 내부
    public void markAsRead() {
        this.isRead = true;
    }

    public String getFormattedCreatedAt(){
        return TimeFormatter.formatCreatedAt(this.getCreatedAt());
    }

}
