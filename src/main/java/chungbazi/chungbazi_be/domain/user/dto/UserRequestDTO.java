package chungbazi.chungbazi_be.domain.user.dto;

import chungbazi.chungbazi_be.domain.user.entity.enums.Education;
import chungbazi.chungbazi_be.domain.user.entity.enums.Employment;
import chungbazi.chungbazi_be.domain.user.entity.enums.Income;
import chungbazi.chungbazi_be.domain.user.entity.enums.Region;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class UserRequestDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL) // null 값 제외
    public static class RegisterDto {
        @Schema(example = "GANGNAM", description = "사용자의 지역")
        private Region region;

        @Schema(example = "EMPLOYED", description = "사용자의 고용 상태")
        private Employment employment;

        @Schema(example = "DECILE_1", description = "사용자의 소득 수준")
        private Income income;

        @Schema(example = "LESS_THAN_HIGH_SCHOOL", description = "사용자의 교육 수준")
        private Education education;

        @Schema(example = "[\"일자리\", \"진로\"]", description = "사용자의 관심 분야")
        private List<String> interests;

        @Schema(example = "[\"중소기업\", \"여성\", \"저소득층\"]", description = "추가 정보")
        private List<String> additionInfo;
    }

}
