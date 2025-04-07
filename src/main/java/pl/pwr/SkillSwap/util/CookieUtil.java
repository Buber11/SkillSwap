package pl.pwr.SkillSwap.util;

import jakarta.servlet.http.Cookie;

public class CookieUtil {

    public static Cookie createTokenCookie(String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge(86400);
        return cookie;
    }
}
