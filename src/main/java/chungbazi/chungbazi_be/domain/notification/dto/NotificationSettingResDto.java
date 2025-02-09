package chungbazi.chungbazi_be.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class NotificationSettingResDto {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class settingResDto {
        boolean policyAlarm;
        boolean communityAlarm;
        boolean rewardAlarm;
        boolean noticeAlarm;
    }
}
