package chungbazi.chungbazi_be.domain.user.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Income {
    DECILE_1,   // 1분위 (소득 하위 10%)
    DECILE_2,   // 2분위 (소득 하위 10~20%)
    DECILE_3,   // 3분위 (소득 하위 20~30%)
    DECILE_4,   // 4분위 (소득 하위 30~40%)
    DECILE_5,   // 5분위 (소득 중간 40~50%)
    DECILE_6,   // 6분위 (소득 중간 50~60%)
    DECILE_7,   // 7분위 (소득 중간 60~70%)
    DECILE_8,   // 8분위 (소득 상위 70~80%)
    DECILE_9,   // 9분위 (소득 상위 80~90%)
    DECILE_10;   // 10분위 (소득 상위 90~100%)

    @JsonValue
    public String toJson() {
        return name(); // Enum 이름을 String으로 반환
    }

}
