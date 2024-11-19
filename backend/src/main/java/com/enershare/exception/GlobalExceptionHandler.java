package com.enershare.exception;

import com.enershare.dto.response.ErrorResponse;
import com.enershare.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        logger.warn("Authentication failed: {}", ex.getMessage());
        String code = ResponseUtils.generateCode(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(new ErrorResponse(code, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        logger.warn("Username already exists: {}", ex.getMessage());
        String code = ResponseUtils.generateCode(HttpStatus.CONFLICT);
        return new ResponseEntity<>(new ErrorResponse(code, ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        logger.warn("Username not found: {}", ex.getMessage());
        String code = ResponseUtils.generateCode(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ErrorResponse(code, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        logger.error("Unexpected error: {}", ex.getMessage(), ex);
        String code = ResponseUtils.generateCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(new ErrorResponse(code, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(com.enershare.exception.AuthenticationException.class)
    public ResponseEntity<Object> handleCustomAuthenticationException(com.enershare.exception.AuthenticationException ex) {
        return ResponseEntity
                .status(HttpStatus.valueOf(ex.getCode()))
                .body(new ErrorResponse(String.valueOf(ex.getCode()), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse("400", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParticipantAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleParticipantAlreadyExistsException(ParticipantAlreadyExistsException ex) {
        logger.warn("Participant already exists: {}", ex.getMessage());
        String code = ResponseUtils.generateCode(HttpStatus.CONFLICT);
        return new ResponseEntity<>(new ErrorResponse(code, ex.getMessage()), HttpStatus.CONFLICT);
    }
}