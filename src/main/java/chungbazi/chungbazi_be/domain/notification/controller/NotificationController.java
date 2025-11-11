package chungbazi.chungbazi_be.domain.notification.controller;

import chungbazi.chungbazi_be.domain.notification.dto.NotificationResponseDTO;
import chungbazi.chungbazi_be.domain.notification.dto.NotificationSettingReqDto;
import chungbazi.chungbazi_be.domain.notification.dto.NotificationSettingResDto;
import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;
import chungbazi.chungbazi_be.domain.notification.service.NotificationService;
import chungbazi.chungbazi_be.domain.notification.service.NotificationSettingService;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    private final NotificationSettingService notificationSettingService;

    @PatchMapping("/{notificationId}/read")
    @Operation(summary = "특정 알림 읽음 상태 변경 API")
    public ApiResponse<Void> readNotification(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ApiResponse.onSuccess(null);
    }

    @GetMapping
    @Operation(summary = "알림 조회 API",description = "알림을 조회하는 API입니다. 전체 알림을 조회하고 싶으면 type 입력을 안하시면 됩니당. 캘린더 알림 조회 시 type에 POLICY_ALARM을 커뮤니티 알림 조회 시에는 COMMUNITY_ALARM을 선택해주세요")
    public ApiResponse<NotificationResponseDTO.notificationListDto> getNotifications(
            @RequestParam(required = false) NotificationType type,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "15") int limit){

        NotificationResponseDTO.notificationListDto response=notificationService.getNotifications(type,cursor,limit);

        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/settings-up")
    @Operation(summary = "알림 수신 설정 api",description = """
            알림 수신을 설정하는 api입니다.
            * policy_alarm은 캘린더 정책 알림 수신 설정,
            * community_alarm은 커뮤니티 관련 알림 수신 설정,
            * reward_alarm은 리워드 알림 수신 설정,
            * notice_alarm은 공지사항 알림 수신 설정으로,
            알림 끄기를 원한다면 false를, 알림 켜기를 원한다면 true를 입력해주시면 됩니다.
            """)
    public ApiResponse<NotificationSettingResDto.settingResDto> updateNotificationSetting(
            @RequestBody NotificationSettingReqDto request
    ) {
        NotificationSettingResDto.settingResDto response=notificationSettingService.setNotificationSetting(request);
        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/settings")
    @Operation(summary = "알림 수신 설정 조회 api",description = "현재 유저의 알림 수신 설정을 조회하는 API입니다")
    public ApiResponse<NotificationSettingResDto.settingResDto> getNotificationSetting() {
        NotificationSettingResDto.settingResDto response=notificationSettingService.getNotificationSetting();
        return ApiResponse.onSuccess(response);
    }




}
