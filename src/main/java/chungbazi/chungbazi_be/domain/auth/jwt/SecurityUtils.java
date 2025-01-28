package chungbazi.chungbazi_be.domain.auth.jwt;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
public class SecurityUtils {
    public static Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new NotFoundHandler(ErrorStatus.NOT_FOUND_USER);
        }
        return Long.valueOf(authentication.getName());
        }
}


