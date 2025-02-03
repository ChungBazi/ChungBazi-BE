package chungbazi.chungbazi_be.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSettingReqDto {

    private boolean policyAlarm;
    private boolean communityAlarm;
    private boolean rewardAlarm;
    private boolean noticeAlarm;


}
