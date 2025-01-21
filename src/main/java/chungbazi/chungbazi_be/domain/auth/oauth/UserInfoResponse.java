package chungbazi.chungbazi_be.domain.auth.oauth;


import chungbazi.chungbazi_be.domain.user.entity.enums.Gender;
import chungbazi.chungbazi_be.domain.user.entity.enums.OAuthProvider;

public interface UserInfoResponse {
    String getEmail();
    String getNickname();
    String getImageUrl();
    String getBirthYear();
    String getBirthDay();
    Gender getGender();
    String getName();
    OAuthProvider getOAuthProvider();
}