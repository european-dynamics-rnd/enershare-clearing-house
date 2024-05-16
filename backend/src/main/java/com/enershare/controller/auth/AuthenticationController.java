package com.enershare.controller.auth;

import com.enershare.dto.auth.AuthenticationRequest;
import com.enershare.dto.auth.AuthenticationResponse;
//import com.enershare.dto.auth.RegisterRequest;
//import com.enershare.dto.auth.RegisterRequest;
import com.enershare.dto.auth.RegisterRequest;
import com.enershare.service.auth.AuthenticationService;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

//    @PostMapping("/register")
//    public void register(@RequestBody RegisterRequest request) {
//        authenticationService.register(request);
//    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, java.io.IOException {
        authenticationService.refreshToken(request, response);
    }


}
