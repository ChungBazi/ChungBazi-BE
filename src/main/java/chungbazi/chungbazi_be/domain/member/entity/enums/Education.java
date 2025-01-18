package chungbazi.chungbazi_be.domain.member.entity.enums;

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

    public static Education fromString(String value) {
        try {
            return Education.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new RuntimeException("Invalid education value: " + value);
        }
    }
}
