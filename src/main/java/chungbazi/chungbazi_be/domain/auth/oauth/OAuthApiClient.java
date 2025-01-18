package chungbazi.chungbazi_be.domain.auth.oauth;

import chungbazi.chungbazi_be.domain.member.entity.enums.OAuthProvider;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);

    String requestAccessTokenWithRefreshToken(String refreshToken); // 리프레시 토큰으로 액세스 토큰 갱신
    void logout(String accessToken); // Logout user
    void deleteAccount(String accessToken); // Delete user account

}