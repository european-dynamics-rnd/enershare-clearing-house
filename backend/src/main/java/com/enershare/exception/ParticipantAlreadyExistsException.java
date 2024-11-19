package com.enershare.exception;

/**
 * @author vtopa
 * @created 19/11/2024 - 13:58
 */
public class ParticipantAlreadyExistsException extends RuntimeException {
    public ParticipantAlreadyExistsException(String message) {
        super(message);
    }
}
