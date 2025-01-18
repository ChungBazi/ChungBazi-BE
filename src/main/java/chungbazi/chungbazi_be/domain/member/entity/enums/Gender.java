package chungbazi.chungbazi_be.domain.member.entity.enums;

public enum Gender {
    FEMALE, MALE, NONE;

    public static Gender fromString(String gender) {
        if ("male".equalsIgnoreCase(gender)) {
            return MALE;
        } else if ("female".equalsIgnoreCase(gender)) {
            return FEMALE;
        }
        throw new IllegalArgumentException("Invalid gender: " + gender);
    }
}
