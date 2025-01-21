package chungbazi.chungbazi_be.domain.auth.controller;

import chungbazi.chungbazi_be.domain.auth.jwt.TokenResponse;
import chungbazi.chungbazi_be.domain.auth.oauth.provider.kakao.KakaoLoginRequestParams;
import chungbazi.chungbazi_be.domain.auth.service.OAuthLoginService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/api/auth")
    public class KakaoAuthController {

        private final OAuthLoginService oAuthLoginService;
        @PostMapping("/kakao-login")
        @Operation(summary = "카카로 로그인 API", description = "authenticationCode를 받아서 accessToken, refreshToken을 얻음")
        public ResponseEntity<Map<String, Object>> loginKakao(@RequestBody KakaoLoginRequestParams params) {
            TokenResponse tokenResponse = oAuthLoginService.login(params);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Successfully logged in");
            response.put("data", tokenResponse);

            return ResponseEntity.ok(response);
        }
    }
