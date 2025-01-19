package chungbazi.chungbazi_be.domain.member.entity.enums;

import chungbazi.chungbazi_be.domain.member.MemberHandler;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;

public enum Gender {
    FEMALE, MALE, NONE;

    public static Gender fromString(String gender) {
        if ("male".equalsIgnoreCase(gender)) {
            return MALE;
        } else if ("female".equalsIgnoreCase(gender)) {
            return FEMALE;
        }
        throw new MemberHandler(ErrorStatus.INVALID_GENDER);
    }
}
