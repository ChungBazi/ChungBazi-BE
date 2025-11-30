package chungbazi.chungbazi_be.domain.auth.jwt;

import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
public class SecurityUtils {
    public static Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 객체가 없는 경우 (토큰 없음 or 잘못된 요청)
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BadRequestHandler(ErrorStatus.EMPTY_TOKEN);
        }

        // 익명 사용자 (`anonymousUser`)로 설정된 경우 (토큰 없이 요청됨)
        if ("anonymousUser".equals(authentication.getPrincipal())) {
            throw new BadRequestHandler(ErrorStatus.EMPTY_TOKEN);
        }

        // 사용자 ID 변환 실패 (예상치 못한 ID 형식)
        try {
            return Long.valueOf(authentication.getName());
        } catch (NumberFormatException e) {
            throw new BadRequestHandler(ErrorStatus.INVALID_USER_ID);
        }
    }
}


