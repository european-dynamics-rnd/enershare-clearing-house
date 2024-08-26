package com.enershare.utils;

import org.springframework.http.HttpStatus;

public class ResponseUtils {

    public static String generateCode(HttpStatus status) {
        return String.format("%03d", status.value());
    }
}

