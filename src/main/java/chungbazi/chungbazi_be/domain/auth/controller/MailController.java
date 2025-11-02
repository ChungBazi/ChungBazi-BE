package chungbazi.chungbazi_be.domain.auth.controller;

import chungbazi.chungbazi_be.domain.auth.dto.TokenRequestDTO;
import chungbazi.chungbazi_be.domain.auth.service.MailService;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class MailController {
    private final MailService mailService;

    @PostMapping("/verification-requests/no-authorization")
    @Operation(summary = "인증 번호 전송 API(로그인X)", description = "인증 번호를 이메일로 보낸다.(로그인X)")
    public ApiResponse<String> sendMessageWithNoAuthorization(String email) {
        mailService.sendCodeToEmailWithNoAuthorization(email);
        return ApiResponse.onSuccess("인증번호 전송이 완료되었습니다.");
    }

    @PostMapping("/verification-requests")
    @Operation(summary = "인증 번호 전송 API", description = "인증 번호를 이메일로 보낸다.")
    public ApiResponse<String> sendMessage() {
        mailService.sendCodeToEmail();
        return ApiResponse.onSuccess("인증번호 전송이 완료되었습니다.");
    }

    @Operation(summary = "인증 번호 검증 API", description = "이메일로 전송 받은 인증번호를 입력한다.")
    @PostMapping("/verification")
    public ApiResponse<String> verificationEmail(@RequestBody @Valid TokenRequestDTO.AuthCodeRequestDTO request) {
        mailService.verifiedCode(request.getEmail(),request.getAuthCode());
        return ApiResponse.onSuccess("성공적으로 인증되었습니다.");
    }
}
