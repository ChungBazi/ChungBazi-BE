package chungbazi.chungbazi_be.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenDTO {
    private String accessToken;
    private String refreshToken;
    private String grantType;
    private long accessExp;
    private long refreshExp;
    private Long userId;
    private String userName;
    private Boolean isFirst;
    public static TokenDTO of(String accessToken,
                              String refreshToken,
                              String grantType,
                              long accessExp,
                              long refreshExp,
                              Long userId,
                              String userName,
                              Boolean isFirst) {
        return new TokenDTO(
                accessToken,
                refreshToken,
                grantType,
                accessExp,
                refreshExp,
                userId,
                userName,
                isFirst);
    }
}