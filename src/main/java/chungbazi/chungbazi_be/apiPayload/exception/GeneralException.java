package chungbazi.chungbazi_be.apiPayload.exception;

import chungbazi.chungbazi_be.apiPayload.BaseErrorCode;
import chungbazi.chungbazi_be.apiPayload.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseErrorCode code;

    public ErrorReasonDTO getErrorReason(){
        return this.code.getReason();
    }

    public  ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}
