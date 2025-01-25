package chungbazi.chungbazi_be.domain.user.dto;

import chungbazi.chungbazi_be.domain.user.entity.enums.Education;
import chungbazi.chungbazi_be.domain.user.entity.enums.Employment;
import chungbazi.chungbazi_be.domain.user.entity.enums.Income;
import chungbazi.chungbazi_be.domain.user.entity.enums.Region;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
        private Region region;
        private Employment employment;
        private Income income;
        private Education education;
        private List<String> interests;
        private List<String> additionInfo;
    }

}
