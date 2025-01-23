package chungbazi.chungbazi_be.global.apiPayload.exception.handler;

import chungbazi.chungbazi_be.global.apiPayload.code.BaseErrorCode;
import chungbazi.chungbazi_be.global.apiPayload.exception.GeneralException;

public class BadRequestHandler extends GeneralException {
    public BadRequestHandler(BaseErrorCode errorCode) {super(errorCode);}
}
