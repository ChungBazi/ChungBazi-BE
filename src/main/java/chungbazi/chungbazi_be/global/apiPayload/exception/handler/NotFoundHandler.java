package chungbazi.chungbazi_be.global.apiPayload.exception.handler;

import chungbazi.chungbazi_be.global.apiPayload.code.BaseErrorCode;
import chungbazi.chungbazi_be.global.apiPayload.exception.GeneralException;

public class NotFoundHandler extends GeneralException {
    public NotFoundHandler(BaseErrorCode baseErrorCode) {super(baseErrorCode);}
}
