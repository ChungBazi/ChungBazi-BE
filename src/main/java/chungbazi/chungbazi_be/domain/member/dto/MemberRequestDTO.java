package chungbazi.chungbazi_be.domain.member.dto;

import chungbazi.chungbazi_be.domain.member.entity.enums.Education;
import chungbazi.chungbazi_be.domain.member.entity.enums.Employment;
import chungbazi.chungbazi_be.domain.member.entity.enums.Income;
import chungbazi.chungbazi_be.domain.member.entity.enums.Region;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class MemberRequestDTO {
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
    public static class AdditionDto {
        @JsonProperty("addition")
        private List<String> addition;
    }

    @Getter
    @NoArgsConstructor
    public static class InterestDto {
        @JsonProperty("interest")
        private List<String> interest;
    }

}
