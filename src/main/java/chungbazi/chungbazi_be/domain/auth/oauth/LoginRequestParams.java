package chungbazi.chungbazi_be.domain.auth.oauth;

import chungbazi.chungbazi_be.domain.user.entity.enums.OAuthProvider;
import org.springframework.util.MultiValueMap;

public interface LoginRequestParams {
    OAuthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}
