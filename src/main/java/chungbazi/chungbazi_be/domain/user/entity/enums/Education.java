package chungbazi.chungbazi_be.domain.user.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Education {
    LESS_THAN_HIGH_SCHOOL,   // 고졸 미만
    HIGH_SCHOOL_ENROLLED,    // 고교 재학
    HIGH_SCHOOL_GRADUATING,  // 고졸 예정
    HIGH_SCHOOL_GRADUATED,   // 고교 졸업
    COLLEGE_ENROLLED,        // 대학 재학
    COLLEGE_GRADUATING,      // 대졸 예정
    COLLEGE_GRADUATED,       // 대학 졸업
    MASTER_DEGREE,           // 석사
    DOCTORAL_DEGREE;        // 박사

    @JsonValue
    public String toJson() {
        return name(); // Enum 이름을 String으로 반환
    }

}
