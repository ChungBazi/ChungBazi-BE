package chungbazi.chungbazi_be.domain.auth.service;

import chungbazi.chungbazi_be.domain.auth.jwt.TokenResponse;
import chungbazi.chungbazi_be.domain.auth.jwt.TokenGenerator;
import chungbazi.chungbazi_be.domain.auth.oauth.LoginRequestParams;
import chungbazi.chungbazi_be.domain.auth.oauth.RequestOAuthInfoService;
import chungbazi.chungbazi_be.domain.auth.oauth.UserInfoResponse;
import chungbazi.chungbazi_be.domain.user.repository.UserRepository;
import chungbazi.chungbazi_be.domain.user.entity.User;


import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public TokenResponse login(LoginRequestParams params) {
        try {
            UserInfoResponse userInfoResponse = requestOAuthInfoService.request(params);
            Long memberId = findOrCreateMember(userInfoResponse);
            return tokenGenerator.generate(memberId);
        } catch (Exception ex) {
            throw new GeneralException(ErrorStatus.AUTHENTICATION_FAILED);
        }
    }

    private Long findOrCreateMember(UserInfoResponse userInfoResponse) {
        return userRepository.findByEmail(userInfoResponse.getEmail())
                .map(User::getId)
                .orElseGet(() -> newMember(userInfoResponse));
    }

    private Long newMember(UserInfoResponse userInfoResponse) {
        User user = User.builder()
                .email(userInfoResponse.getEmail())
                .nickname(userInfoResponse.getNickname())
                .name(userInfoResponse.getName())
                .birthYear(userInfoResponse.getBirthYear())
                .birthDay(userInfoResponse.getBirthDay())
                .gender(userInfoResponse.getGender())
                .imageUrl(userInfoResponse.getImageUrl())
                .oAuthProvider(userInfoResponse.getOAuthProvider())
                .build();

        return userRepository.save(user).getId();
    }
}