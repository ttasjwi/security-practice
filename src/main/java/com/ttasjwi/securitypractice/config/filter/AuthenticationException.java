package com.ttasjwi.securitypractice.config.filter;

import com.ttasjwi.securitypractice.config.exception.CustomException;

public class AuthenticationException extends CustomException {

    public AuthenticationException(String message) {
        super(message);
    }
}
