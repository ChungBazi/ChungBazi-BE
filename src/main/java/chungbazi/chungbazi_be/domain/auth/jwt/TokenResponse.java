package chungbazi.chungbazi_be.domain.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    private String grantType;
    private Long expiresIn;

    public static TokenResponse of(String accessToken, String refreshToken, String grantType, Long expiresIn) {
        return new TokenResponse(accessToken, refreshToken, grantType, expiresIn);
    }
}