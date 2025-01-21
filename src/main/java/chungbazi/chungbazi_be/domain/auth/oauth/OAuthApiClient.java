package chungbazi.chungbazi_be.domain.auth.oauth;


import chungbazi.chungbazi_be.domain.user.entity.enums.OAuthProvider;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();
    String requestAccessToken(LoginRequestParams params);
    UserInfoResponse requestOauthInfo(String accessToken);

}