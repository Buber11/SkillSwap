package pl.pwr.SkillSwap.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.pwr.SkillSwap.dto.AuthRequest;
import pl.pwr.SkillSwap.dto.UserPostRequest;
import pl.pwr.SkillSwap.exception.NotValidTokenException;
import pl.pwr.SkillSwap.security.JwtUtil;
import pl.pwr.SkillSwap.security.SecurityUserDetails;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public String login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.username(), authRequest.password()
                )
        );
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        return jwtUtil.generateToken(userDetails.getUsername());
    }

    public void register(UserPostRequest userPostRequest){
        userService.createUser(userPostRequest);
    }


    public String refreshToken(String token) {

        long refreshThreshold = 300000;

        if (!jwtUtil.isTokenValidCheck(token)) {
            throw new NotValidTokenException("Token is invalid or expired");
        }

        if (!jwtUtil.shouldRefreshToken(token, refreshThreshold)) {
            return token;
        }

        String username = jwtUtil.extractUsername(token);
        return jwtUtil.generateToken(username);
    }
}
