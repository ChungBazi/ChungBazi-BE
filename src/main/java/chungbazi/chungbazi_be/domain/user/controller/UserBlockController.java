package chungbazi.chungbazi_be.domain.user.controller;

import chungbazi.chungbazi_be.domain.user.service.UserBlockService;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserBlockController {
    private final UserBlockService userBlockService;

    @PostMapping("/block/{blockedUserId}")
    @Operation(summary = "유저 차단 API", description = "특정 유저를 차단하는 API입니다.")
    public ApiResponse<Void> blockUser(@PathVariable Long blockedUserId){
        userBlockService.blockUser(blockedUserId);
        return ApiResponse.onSuccess(null);
    }

    @PatchMapping("/block/{blockedUserId}")
    @Operation(summary = "유저 차단 해제 API", description = "특정 유저의 차단을 해제하는 API입니다.")
    public ApiResponse<Void> unblockUser(@PathVariable Long blockedUserId){
        userBlockService.unblockUser(blockedUserId);
        return ApiResponse.onSuccess(null);
    }
}
