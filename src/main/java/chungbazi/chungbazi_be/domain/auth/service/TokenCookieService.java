package chungbazi.chungbazi_be.domain.auth.service;

import chungbazi.chungbazi_be.domain.auth.dto.TokenDTO;
import chungbazi.chungbazi_be.domain.auth.jwt.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenCookieService {

    public void addTokenCookies(HttpServletResponse response, TokenDTO token) {
        CookieUtil.addTokenCookies(response,
                token.getAccessToken(),
                token.getAccessExp(),
                token.getRefreshToken(),
                token.getRefreshExp());
    }
}