package com.enershare.exception;

import com.enershare.dto.response.ErrorResponse;
import com.enershare.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        logger.warn("Authentication failed: {}", ex.getMessage());
        String code = ResponseUtils.generateCode(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(new ErrorResponse(code, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        logger.warn("Email already exists: {}", ex.getMessage());
        String code = ResponseUtils.generateCode(HttpStatus.CONFLICT);
        return new ResponseEntity<>(new ErrorResponse(code, ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmailNotFoundException(EmailNotFoundException ex) {
        logger.warn("Email not found: {}", ex.getMessage());
        String code = ResponseUtils.generateCode(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ErrorResponse(code, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        logger.error("Unexpected error: {}", ex.getMessage(), ex);
        String code = ResponseUtils.generateCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(new ErrorResponse(code, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}