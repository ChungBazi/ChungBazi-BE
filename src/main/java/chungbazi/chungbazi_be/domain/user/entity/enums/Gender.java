package chungbazi.chungbazi_be.domain.user.entity.enums;

import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;

public enum Gender {
    FEMALE, MALE, NONE;

    public static Gender fromString(String gender) {
        if ("male".equalsIgnoreCase(gender)) {
            return MALE;
        } else if ("female".equalsIgnoreCase(gender)) {
            return FEMALE;
        }
        throw new BadRequestHandler(ErrorStatus.INVALID_GENDER);
    }
}
