package chungbazi.chungbazi_be.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ProfileDto {
        Long userId;
        String name;
        String email;
        String profileImg;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    public static class ProfileUpdateDto {
        Long userId;
        String name;
        String profileImg;
    }

}
