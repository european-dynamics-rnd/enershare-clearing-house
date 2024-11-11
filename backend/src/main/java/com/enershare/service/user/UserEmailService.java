package com.enershare.service.user;

import com.enershare.service.auth.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEmailService {

    private final JwtService jwtService;

    public String getTokenFromRequest(HttpServletRequest request) {
        return jwtService.getJwt(request);
    }

    public String getEmailFromToken(String token) {
        return jwtService.getUserIdByToken(token);
    }

    // this function encapsulates getTokenFromRequest and getEmailFromToken
    public String getEmailFromRequest(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        return getEmailFromToken(token);
    }
}
