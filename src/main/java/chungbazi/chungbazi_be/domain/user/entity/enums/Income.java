package chungbazi.chungbazi_be.domain.user.entity.enums;

import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Income {
    DECILE_1("1분위"),
    DECILE_2("2분위"),
    DECILE_3("3분위"),
    DECILE_4("4분위"),
    DECILE_5("5분위"),
    DECILE_6("6분위"),
    DECILE_7("7분위"),
    DECILE_8("8분위"),
    DECILE_9("9분위"),
    DECILE_10("10분위");

    private final String description;

    Income(String description) {
        this.description = description;
    }

    @JsonValue
    public String toJson() {
        return description; // 한글 값 반환
    }

    @JsonCreator
    public static Income fromJson(String value) {
        return Arrays.stream(Income.values())
                .filter(e -> e.description.equals(value))
                .findFirst()
                .orElseThrow(() -> new BadRequestHandler(ErrorStatus.INVALID_VALUE));
    }
}
