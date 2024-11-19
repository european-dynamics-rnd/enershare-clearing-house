package com.enershare.service.user;

import com.enershare.service.auth.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsernameService {

    private final JwtService jwtService;

    public String getTokenFromRequest(HttpServletRequest request) {
        return jwtService.getJwt(request);
    }

    public String getUsernameFromToken(String token) {
        return jwtService.getUserIdByToken(token);
    }

    // this function encapsulates getTokenFromRequest and getUserNameFromToken
    public String getUsernameFromRequest(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        return getUsernameFromToken(token);
    }
}
