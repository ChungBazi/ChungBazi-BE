package chungbazi.chungbazi_be.domain.member.dto;

import chungbazi.chungbazi_be.domain.member.entity.enums.Education;

public class MemberResponseDTO {
    public static class EducationDto{
        private Long memberId;
        private Education education;
    }
}
