package chungbazi.chungbazi_be.domain.auth.dto;
import chungbazi.chungbazi_be.global.validation.RegexConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequestDTO {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    @Schema(example = "김바지", description = "사용자의 닉네임")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Pattern(
            regexp = RegexConstants.EMAIL_REGEX,
            message = "유효한 이메일 주소여야 합니다."
    )
    @Schema(example = "chungbazi@example.com", description = "유효한 이메일 주소")
    private String email;
}