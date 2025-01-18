package chungbazi.chungbazi_be.domain.auth.oauth;

import chungbazi.chungbazi_be.domain.member.entity.enums.OAuthProvider;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    OAuthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}
