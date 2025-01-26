package chungbazi.chungbazi_be.domain.auth.jwt;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void addTokenCookies(
            HttpServletResponse response,
            String accessToken,
            long accessExp,
            String refreshToken,
            long refreshExp) {

        addCookie(response, "accessToken", accessToken, (int) accessExp);
        addCookie(response, "refreshToken", refreshToken, (int) refreshExp); // Refresh token 7일
    }

}
