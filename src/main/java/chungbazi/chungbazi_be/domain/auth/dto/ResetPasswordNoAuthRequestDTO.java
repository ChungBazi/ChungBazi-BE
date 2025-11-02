package chungbazi.chungbazi_be.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ResetPasswordNoAuthRequestDTO {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String authCode;

    @NotBlank
    private String newPassword;
}
