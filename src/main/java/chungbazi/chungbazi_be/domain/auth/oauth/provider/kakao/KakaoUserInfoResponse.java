package chungbazi.chungbazi_be.domain.auth.oauth.provider.kakao;

import chungbazi.chungbazi_be.domain.auth.oauth.UserInfoResponse;
import chungbazi.chungbazi_be.domain.user.entity.enums.Gender;
import chungbazi.chungbazi_be.domain.user.entity.enums.OAuthProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfoResponse implements UserInfoResponse {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoAccount {
        private KakaoProfile profile;
        private String email;
        private String name;
        private String birthyear;
        private String birthday;
        private String gender;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoProfile {
        private String nickname;
        private String profile_image_url;
    }

    @Override
    public String getEmail() {
        return kakaoAccount.email;
    }
    @Override
    public String getNickname() {
        return kakaoAccount.profile.nickname;
    }
    @Override
    public String getImageUrl() {
        return kakaoAccount.profile.profile_image_url;
    }
    @Override
    public String getBirthYear() {
        return kakaoAccount.birthyear;
    }
    @Override
    public String getBirthDay() {
        return kakaoAccount.birthday;
    }
    @Override
    public Gender getGender() {
        return Gender.fromString(kakaoAccount.gender);
    }
    @Override
    public String getName() {
        return kakaoAccount.name;
    }
    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.KAKAO;
    }
}