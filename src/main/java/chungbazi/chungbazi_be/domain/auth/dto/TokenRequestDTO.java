package chungbazi.chungbazi_be.domain.auth.dto;
import chungbazi.chungbazi_be.global.validation.RegexConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class TokenRequestDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginTokenRequestDTO {

        @NotBlank(message = "이름은 필수 입력 항목입니다.")
        @Size(min = 1, max = 10, message = "이름은 10자 이하")
        @Schema(example = "김바지", description = "사용자의 이름")
        private String name;

        @NotBlank(message = "이메일은 필수 입력 항목입니다.")
        @Pattern(
                regexp = RegexConstants.EMAIL_REGEX,
                message = "유효한 이메일 주소여야 합니다."
        )
        @Schema(example = "chungbazi@example.com", description = "유효한 이메일 주소")
        private String email;

        @Schema(example = "eKD_mr9SG0XftaFoKFB9L5ABA91bF1cpjv7g6Khw...", description = "유효한 FCM Token")
        private String fcmToken;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class refreshTokenRequestDTO{
        @NotBlank
        @Schema(example = "eKD_mr9SG0XftaFoKFB9L5ABA91bF1cpjv7g6Khw...", description = "유효한 refresh Token")
        private String refreshToken;
    }
}