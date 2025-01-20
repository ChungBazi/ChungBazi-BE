package chungbazi.chungbazi_be.domain.user.dto;

import chungbazi.chungbazi_be.domain.user.entity.enums.Education;
import chungbazi.chungbazi_be.domain.user.entity.enums.Employment;
import chungbazi.chungbazi_be.domain.user.entity.enums.Income;
import chungbazi.chungbazi_be.domain.user.entity.enums.Region;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class UserRequestDTO {
    @Getter
    @NoArgsConstructor
    public static class EducationDto{
        @JsonProperty("education")
        private Education education;

    }
    @Getter
    @NoArgsConstructor
    public static class EmploymentDto{
        @JsonProperty("employment")
        private Employment employment;

    }
    @Getter
    @NoArgsConstructor
    public static class IncomeDto{
        @JsonProperty("income")
        private Income income;

    }
    @Getter
    @NoArgsConstructor
    public static class RegionDto{
        @JsonProperty("region")
        private Region region;

    }

    @Getter
    @NoArgsConstructor
    public static class InterestDto {
        private List<String> interests; // 관심사 목록 (예: ["음악", "스포츠", "영화"])
    }

    @Getter
    @NoArgsConstructor
    public static class AdditionDto {
        private List<String> additionInfo; // 추가 정보 목록 (예: ["중소기업", "여성"])
    }

}
