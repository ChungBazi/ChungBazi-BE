package chungbazi.chungbazi_be.domain.auth.oauth.provider.kakao;

import chungbazi.chungbazi_be.domain.auth.oauth.LoginRequestParams;
import chungbazi.chungbazi_be.domain.user.entity.enums.OAuthProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@NoArgsConstructor
public class KakaoLoginRequestParams implements LoginRequestParams {
    private String authorizationCode;

    public KakaoLoginRequestParams(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }
    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        return body;
    }
}