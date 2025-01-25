package chungbazi.chungbazi_be.global.apiPayload.code.status;

import chungbazi.chungbazi_be.global.apiPayload.code.BaseErrorCode;
import chungbazi.chungbazi_be.global.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    //일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // Not Found
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "USER404", "해당 유저를 찾을 수 없습니다."),

    // 멤버 관련 에러
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임은 필수 입니다."),
    INVALID_GENDER(HttpStatus.BAD_REQUEST, "MEMBER", "유효하지않은 성별입니다."),

    //Policy
    CATEGORY_CODE_NOT_FOUND(HttpStatus.BAD_REQUEST, "POLICY5001", "존재하지 않는 정책 코드 입니다."),
    NO_SEARCH_NAME(HttpStatus.BAD_REQUEST, "POLICY5002", "검색어를 입력해주세요."),
    POLICY_NOT_FOUND(HttpStatus.NOT_FOUND, "POLICY5003", "존재하지 않는 정책입니다."),
    NO_CURSOR(HttpStatus.BAD_REQUEST, "POLICY5004", "커서가 존재하지않습니다."),

    //Notification
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND,"NOTIFICATION001","알림이 존재하지 않습니다."),
    GOOGLE_REQUEST_TOKEN_ERROR(HttpStatus.UNAUTHORIZED,"FCMTOKEN001","firebase 접근 토큰이 유효하지 않습니다."),
    FCM_SEND_FAILURE(HttpStatus.BAD_REQUEST,"FCMSEND001", "FCM 메시지 전송에 실패했습니다."),


    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
