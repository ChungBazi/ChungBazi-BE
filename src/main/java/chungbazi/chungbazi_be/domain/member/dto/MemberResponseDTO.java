package chungbazi.chungbazi_be.domain.member.dto;

import chungbazi.chungbazi_be.domain.member.entity.enums.Education;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class MemberResponseDTO {
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
