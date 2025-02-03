package chungbazi.chungbazi_be.domain.notification.converter;

import chungbazi.chungbazi_be.domain.notification.dto.NotificationResponseDTO;
import chungbazi.chungbazi_be.domain.notification.dto.NotificationSettingResDto;
import chungbazi.chungbazi_be.domain.notification.entity.Notification;
import chungbazi.chungbazi_be.domain.notification.entity.NotificationSetting;

public class NotificationConverter {

    public static NotificationResponseDTO.notificationDto toNotificationDto(Notification notification){
        return NotificationResponseDTO.notificationDto.builder()
                .notificationId(notification.getId())
                .isRead(notification.isRead())
                .message(notification.getMessage())
                .type(notification.getType())
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
