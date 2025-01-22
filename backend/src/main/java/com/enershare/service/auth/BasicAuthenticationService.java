package com.enershare.service.auth;

import com.enershare.utils.RunnableWithReturn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class BasicAuthenticationService {

    private static final String VALID_USERNAME = "enershare";
    private static final String VALID_PASSWORD = "enersh@r3";

    public <T> ResponseEntity<T> authenticateAndExecute(String authHeader, RunnableWithReturn<ResponseEntity<T>> action) {
        String[] credentials = decodeAuthHeader(authHeader);
        if (credentials == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String username = credentials[0];
        String password = credentials[1];
        if (!isValidCredentials(username, password)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return action.run();

    }
    private String[] decodeAuthHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            return null;
        }
        try {
            String base64Credentials = authHeader.substring("Basic ".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            final String[] values = credentials.split(":", 2);
            if (values.length != 2) {
                return null;
            }
            return values;
        } catch (IllegalArgumentException e) {
            // This catches exceptions from Base64 decoding
            return null;
        }
    }
    private boolean isValidCredentials(String username, String password) {
        return VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password);
    }

}
