package chungbazi.chungbazi_be.domain.user.entity.enums;

import chungbazi.chungbazi_be.global.apiPayload.exception.handler.UserHandler;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;

public enum Gender {
    FEMALE, MALE, NONE;

    public static Gender fromString(String gender) {
        if ("male".equalsIgnoreCase(gender)) {
            return MALE;
        } else if ("female".equalsIgnoreCase(gender)) {
            return FEMALE;
        }
        throw new UserHandler(ErrorStatus.INVALID_GENDER);
    }
}
