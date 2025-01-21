package chungbazi.chungbazi_be.domain.auth.oauth.provider.kakao;

import chungbazi.chungbazi_be.domain.auth.oauth.OAuthApiClient;
import chungbazi.chungbazi_be.domain.auth.oauth.UserInfoResponse;
import chungbazi.chungbazi_be.domain.auth.oauth.LoginRequestParams;
import chungbazi.chungbazi_be.domain.user.entity.enums.OAuthProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoApiClient implements OAuthApiClient {

    private static final String GRANT_TYPE = "authorization_code";

    @Value("${oauth.kakao.url.auth}")
    private String authUrl;

    @Value("${oauth.kakao.url.api}")
    private String apiUrl;

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    private final RestTemplate restTemplate;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public String requestAccessToken(LoginRequestParams params) {
        String url = authUrl + "/oauth/token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        KakaoTokenResponse response = restTemplate.postForObject(url, request, KakaoTokenResponse.class);

        assert response != null;
        return response.getAccessToken();
    }

    @Override
    public UserInfoResponse requestOauthInfo(String accessToken) {
        String url = apiUrl + "/v2/user/me";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys", "["
                + "\"kakao_account.email\", "
                + "\"kakao_account.profile.nickname\", "
                + "\"kakao_account.profile.profile_image_url\", "
                + "\"kakao_account.birthyear\", "
                + "\"kakao_account.birthday\", "
                + "\"kakao_account.gender\", "
                + "\"kakao_account.name\""
                + "]"
        );

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        return restTemplate.postForObject(url, request, KakaoUserInfoResponse.class);
    }
}