package chungbazi.chungbazi_be.domain.user.dto;

import chungbazi.chungbazi_be.domain.user.entity.enums.RewardLevel;
import java.util.List;
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
        RewardLevel characterImg;
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
