package chungbazi.chungbazi_be.domain.user.dto;

import chungbazi.chungbazi_be.domain.user.entity.enums.Education;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserResponseDTO {
    public static class EducationDto{
        private Long memberId;
        private Education education;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ProfileDto {
        Long userId;
        String name;
        String email;
    }
}
