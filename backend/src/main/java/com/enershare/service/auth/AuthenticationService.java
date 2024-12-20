package com.enershare.service.auth;

import com.enershare.dto.auth.AuthenticationRequest;
import com.enershare.dto.auth.AuthenticationResponse;
import com.enershare.dto.user.UserDTO;
import com.enershare.enums.TokenType;
import com.enershare.exception.AuthenticationException;
import com.enershare.mapper.UserMapper;
import com.enershare.model.token.Token;
import com.enershare.model.user.User;
import com.enershare.repository.token.TokenRepository;
import com.enershare.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        if ((request.getUsername() == null || request.getUsername().isEmpty()) && (request.getPassword() == null || request.getPassword().isEmpty())) {
            throw new AuthenticationException("Username and password are required", 501);
        } else if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new AuthenticationException("Username is required", 502);
        } else if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new AuthenticationException("Password is required", 503);
        } else {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername()
                                , request.getPassword()));
                var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() ->
                        new AuthenticationException("User not found", 404)
                );

                var jwtToken = jwtService.generateToken(user);
                var refreshToken = jwtService.generateRefreshToken(user);
                revokeAllTokensOfUser(user);
                saveUserToken(user, jwtToken);


                UserDTO userDTO = userMapper.map(user);

                return AuthenticationResponse.
                        builder()
                        .accessToken(jwtToken)
                        .refreshToken(refreshToken)
                        .user(userDTO)
                        .build();
            } catch (BadCredentialsException e) {
                throw new AuthenticationException("Invalid username or password", 401, e);
            }
        }
    }

    private void revokeAllTokensOfUser(User user) {
        var validTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validTokens.isEmpty()) {
            return;
        }
        validTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var expirationDate = jwtService.extractExpiration(jwtToken);
        var token = Token.builder()
                .userId(user.getId())
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .expirationDate(expirationDate)
                .build();
        tokenRepository.save(token);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, java.io.IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = this.userRepository.findByUsername(username).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllTokensOfUser(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse
                        .builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
