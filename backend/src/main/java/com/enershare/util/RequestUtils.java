package com.enershare.util;

import com.enershare.service.auth.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestUtils {

    private final JwtService jwtService;

    @Autowired
    public RequestUtils(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        return jwtService.getJwt(request);
    }

    public String getEmailFromToken(String token) {
        return jwtService.getUserIdByToken(token);
    }
}