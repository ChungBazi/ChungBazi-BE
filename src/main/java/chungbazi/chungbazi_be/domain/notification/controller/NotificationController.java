package chungbazi.chungbazi_be.domain.notification.controller;

import chungbazi.chungbazi_be.domain.notification.dto.NotificationRequestDTO;
import chungbazi.chungbazi_be.domain.notification.dto.NotificationResponseDTO;
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

    @PostMapping
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
}
