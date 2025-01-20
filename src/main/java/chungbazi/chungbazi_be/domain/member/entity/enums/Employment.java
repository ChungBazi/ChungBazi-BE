package chungbazi.chungbazi_be.domain.member.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Employment {
    EMPLOYED,           // 재직자
    SELF_EMPLOYED,      // 자영업자
    UNEMPLOYED,         // 미취업자
    FREELANCER,         // 프리랜서
    DAY_WORKER,         // 일용근로자
    PRE_ENTREPRENEUR,   // 예비창업자
    TEMPORARY_WORKER,   // 단기근로자
    AGRICULTURAL;        // 영농종사자

    @JsonValue
    public String toJson() {
        return name(); // Enum 이름을 String으로 반환
    }
}
