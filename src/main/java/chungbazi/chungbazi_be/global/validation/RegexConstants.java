package chungbazi.chungbazi_be.global.validation;

public class RegexConstants {
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.(com|net|org|co\\.kr|ac\\.kr|gov|edu)$";

    // 비밀번호 정규식: 최소 8자, 대소문자, 숫자, 특수문자 포함
    public static final String PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=`~{}\\[\\]:\";'<>?,./]).{8,}$";
}