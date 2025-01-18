package chungbazi.chungbazi_be.domain.auth.application;

import chungbazi.chungbazi_be.domain.auth.AuthTokens;
import chungbazi.chungbazi_be.domain.auth.infra.kakao.KakaoLoginParams;
import chungbazi.chungbazi_be.domain.auth.jwt.JwtTokenProvider;
import chungbazi.chungbazi_be.domain.auth.oauth.RequestOAuthInfoService;
import chungbazi.chungbazi_be.domain.member.entity.enums.OAuthProvider;
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
    private final RequestOAuthInfoService requestOAuthInfoService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/kakao-login")
    public ResponseEntity<Map<String, Object>> loginKakao(@RequestBody KakaoLoginParams params) {
        AuthTokens authTokens = oAuthLoginService.login(params);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Successfully logged in");
        response.put("data", authTokens);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/kakao-logout")
    public ResponseEntity<Map<String, String>> kakaoLogout(@RequestHeader("Authorization") String accessToken) {

        String token = accessToken.replace("Bearer ", "");

        requestOAuthInfoService.logout(OAuthProvider.KAKAO, token);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully logged out");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete-account")
    public ResponseEntity<Map<String, String>> deleteAccount(@RequestHeader("Authorization") String accessToken) {

        String token = accessToken.replace("Bearer ", "");

        requestOAuthInfoService.deleteAccount(OAuthProvider.KAKAO, token);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully deleted account");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String accessToken) {

        String token = accessToken.replace("Bearer ", "");
        boolean isValid = jwtTokenProvider.validateToken(token);

        Map<String, Object> response = new HashMap<>();
        response.put("isValid", isValid);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user-info")
    public ResponseEntity<Map<String, Object>> getUserInfo(@RequestHeader("Authorization") String accessToken) {

        String token = accessToken.replace("Bearer ", "");
        String subject = jwtTokenProvider.extractSubject(token);
        Long memberId = Long.parseLong(subject);

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", memberId);
        userInfo.put("message", "Token is valid and user is authenticated");
        return ResponseEntity.ok(userInfo);
    }


}
