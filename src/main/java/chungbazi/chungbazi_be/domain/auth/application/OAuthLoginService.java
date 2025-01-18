package chungbazi.chungbazi_be.domain.auth.application;

import chungbazi.chungbazi_be.domain.auth.AuthTokens;
import chungbazi.chungbazi_be.domain.auth.AuthTokensGenerator;
import chungbazi.chungbazi_be.domain.auth.oauth.OAuthInfoResponse;
import chungbazi.chungbazi_be.domain.auth.oauth.OAuthLoginParams;
import chungbazi.chungbazi_be.domain.auth.oauth.RequestOAuthInfoService;
import chungbazi.chungbazi_be.domain.member.entity.Member;
import chungbazi.chungbazi_be.domain.member.entity.enums.OAuthProvider;
import chungbazi.chungbazi_be.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(Member::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        Member member = Member.builder()
                .email(oAuthInfoResponse.getEmail())
                .nickname(oAuthInfoResponse.getNickname())
                .name(oAuthInfoResponse.getName())
                .birthYear(oAuthInfoResponse.getBirthYear())
                .birthDay(oAuthInfoResponse.getBirthDay())
                .gender(oAuthInfoResponse.getGender())
                .imageUrl(oAuthInfoResponse.getImageUrl())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();

        return memberRepository.save(member).getId();
    }

    public void logout(OAuthProvider provider, String accessToken) {
        requestOAuthInfoService.logout(provider, accessToken);

    }


    public void deleteAccount(OAuthProvider provider, String accessToken) {
        requestOAuthInfoService.deleteAccount(provider, accessToken);

    }

}