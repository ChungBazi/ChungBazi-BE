package chungbazi.chungbazi_be.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponseDTO {

    @Getter
    @AllArgsConstructor
    public static class LoginTokenResponseDTO {
        private Long userId;
        private String userName;
        private Boolean isFirst;
        private String accessToken;
        private String refreshToken;
        private long accessExp;

        public static LoginTokenResponseDTO of(Long userId, String userName, Boolean isFirst, String accessToken, String refreshToken, long accessExp) {
            return new LoginTokenResponseDTO(userId, userName, isFirst, accessToken, refreshToken, accessExp);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class RefreshTokenResponseDTO {
        private String accessToken;
        private long accessExp;

        public static RefreshTokenResponseDTO of(String accessToken, long accessExp) {
            return new RefreshTokenResponseDTO(accessToken, accessExp);
        }
    }



}
