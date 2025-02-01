package chungbazi.chungbazi_be.domain.user.entity.enums;

import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import com.fasterxml.jackson.annotation.JsonValue;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum Education {
    LESS_THAN_HIGH_SCHOOL("고졸 미만"),
    HIGH_SCHOOL_ENROLLED("고교 재학"),
    HIGH_SCHOOL_GRADUATING("고졸 예정"),
    HIGH_SCHOOL_GRADUATED("고교 졸업"),
    COLLEGE_ENROLLED("대학 재학"),
    COLLEGE_GRADUATING("대졸 예정"),
    COLLEGE_GRADUATED("대학 졸업"),
    MASTER_DOCTORAL_DEGREE("석사 박사");

    private final String description;

    Education(String description) {
        this.description = description;
    }

    @JsonValue
    public String toJson() {
        return description; // 한글 값 반환
    }

    @JsonCreator
    public static Education fromJson(String value) {
        return Arrays.stream(Education.values())
                .filter(e -> e.description.equals(value))
                .findFirst()
                .orElseThrow(() -> new BadRequestHandler(ErrorStatus.INVALID_VALUE));
    }
}
