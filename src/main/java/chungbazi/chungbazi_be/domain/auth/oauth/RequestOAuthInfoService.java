package chungbazi.chungbazi_be.domain.auth.oauth;

import chungbazi.chungbazi_be.domain.member.entity.enums.OAuthProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RequestOAuthInfoService {
    private final Map<OAuthProvider, OAuthApiClient> clients;

    public RequestOAuthInfoService(List<OAuthApiClient> clients) {
        this.clients = clients.stream().collect(
                Collectors.toUnmodifiableMap(OAuthApiClient::oAuthProvider, Function.identity())
        );
    }

    public OAuthInfoResponse request(OAuthLoginParams params) {
        OAuthApiClient client = clients.get(params.oAuthProvider());
        String accessToken = client.requestAccessToken(params);

        return client.requestOauthInfo(accessToken);
    }

    public void logout(OAuthProvider provider, String accessToken) {
        OAuthApiClient client = clients.get(provider);

        System.out.println("Preparing to logout user with token: " + accessToken);
        client.logout(accessToken);
        System.out.println("Successfully logged out user for provider: " + provider);
    }

    public void deleteAccount(OAuthProvider provider, String accessToken) {
        OAuthApiClient client = clients.get(provider);

        System.out.println("Preparing to delete account for token: " + accessToken);
        client.deleteAccount(accessToken);
        System.out.println("Successfully deleted account for provider: " + provider);
    }


}