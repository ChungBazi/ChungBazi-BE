package chungbazi.chungbazi_be.domain.user.entity.enums;

import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Employment {
    EMPLOYED("재직자"),
    SELF_EMPLOYED("자영업자"),
    UNEMPLOYED("미취업자"),
    FREELANCER("프리랜서"),
    DAY_WORKER("일용근로자"),
    PRE_ENTREPRENEUR("예비창업자"),
    TEMPORARY_WORKER("단기근로자"),
    AGRICULTURAL("영농종사자");

    private final String description;

    Employment(String description) {
        this.description = description;
    }

    @JsonValue
    public String toJson() {
        return description; // 한글 값 반환
    }

    @JsonCreator
    public static Employment fromJson(String value) {
        return Arrays.stream(Employment.values())
                .filter(e -> e.description.equals(value))
                .findFirst()
                .orElseThrow(() -> new BadRequestHandler(ErrorStatus.INVALID_VALUE));
    }
}
