package pl.pwr.SkillSwap.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pwr.SkillSwap.dto.AuthRequest;
import pl.pwr.SkillSwap.dto.UserPostRequest;
import pl.pwr.SkillSwap.service.AuthService;
import pl.pwr.SkillSwap.util.CookieUtil;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        String token = authService.login(authRequest);
        Cookie cookie = CookieUtil.createTokenCookie(token);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
    @PostMapping("register")
    public ResponseEntity<Void> register(@RequestBody UserPostRequest userPostRequest) {
        authService.register(userPostRequest);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(@CookieValue("token") String token, HttpServletResponse response) {
        String newToken = authService.refreshToken(token);
        Cookie cookie = CookieUtil.createTokenCookie(newToken);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        // Tworzymy ciasteczko token o maxAge = 0, aby usunąć token z przeglądarki
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

}
