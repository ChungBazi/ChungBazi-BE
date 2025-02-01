package chungbazi.chungbazi_be.domain.notification.controller;

import chungbazi.chungbazi_be.domain.notification.dto.NotificationRequestDTO;
import chungbazi.chungbazi_be.domain.notification.dto.NotificationResponseDTO;
import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;
import chungbazi.chungbazi_be.domain.notification.service.NotificationService;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/create")
    @Operation(summary = "알림 생성 API", description = "알림을 생성하고, fcm으로 알림을 요청하는 API입니다.")
    public ApiResponse<NotificationResponseDTO.responseDto> createNotification(@RequestBody NotificationRequestDTO.createDTO dto) {
        NotificationResponseDTO.responseDto responseDto = notificationService.sendNotification(dto);
        return ApiResponse.onSuccess(responseDto);
    }

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



}
