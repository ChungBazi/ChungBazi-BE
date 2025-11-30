package chungbazi.chungbazi_be.domain.notification.entity;

import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSetting extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private boolean policyAlarm=true;

    @Builder.Default
    private boolean communityAlarm=true;

    @Builder.Default
    private boolean rewardAlarm=true;

    @Builder.Default
    private boolean noticeAlarm=true;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    public NotificationSetting(User user) {
        this.user = user;
    }

    public void updatePolicyAlarm(boolean policyAlarm) {this.policyAlarm = policyAlarm;}

    public void updateCommunityAlarm(boolean communityAlarm) {this.communityAlarm = communityAlarm;}

    public void updateRewardAlarm(boolean rewardAlarm) {this.rewardAlarm = rewardAlarm;}

    public void updateNoticeAlarm(boolean noticeAlarm) {this.noticeAlarm = noticeAlarm;}


}
