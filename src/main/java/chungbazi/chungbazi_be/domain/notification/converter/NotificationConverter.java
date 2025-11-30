package chungbazi.chungbazi_be.domain.notification.converter;

import chungbazi.chungbazi_be.domain.notification.dto.NotificationResponseDTO;
import chungbazi.chungbazi_be.domain.notification.dto.NotificationSettingResDto;
import chungbazi.chungbazi_be.domain.notification.entity.Notification;
import chungbazi.chungbazi_be.domain.notification.entity.NotificationSetting;
import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;

public class NotificationConverter {

    public static NotificationResponseDTO.notificationDto toNotificationDto(Notification notification){
        Long policyId = null;
        Long postId = null;

        // notification 타입에 따라 관련 ID를 할당
        if (notification.getType() == NotificationType.POLICY_ALARM) {
            if (notification.getPolicy() != null) {
                policyId = notification.getPolicy().getId();
            }
        } else if (notification.getType() == NotificationType.COMMUNITY_ALARM) {
            if (notification.getPost() != null) {
                postId = notification.getPost().getId();
            }
        }

        return NotificationResponseDTO.notificationDto.builder()
                .notificationId(notification.getId())
                .isRead(notification.isRead())
                .message(notification.getMessage())
                .type(notification.getType())
                .policyId(policyId)  // POLICY 타입일 경우에만 값이 들어감
                .postId(postId)      // COMMUNITY 타입일 경우에만 값이 들어감
                .formattedCreatedAt(notification.getFormattedCreatedAt())
                .build();
    }

    public static NotificationSettingResDto.settingResDto toSettingResDto(NotificationSetting notificationSetting){
        return NotificationSettingResDto.settingResDto.builder()
                .policyAlarm(notificationSetting.isPolicyAlarm())
                .communityAlarm(notificationSetting.isCommunityAlarm())
                .rewardAlarm(notificationSetting.isRewardAlarm())
                .noticeAlarm(notificationSetting.isNoticeAlarm())
                .build();
    }
}
