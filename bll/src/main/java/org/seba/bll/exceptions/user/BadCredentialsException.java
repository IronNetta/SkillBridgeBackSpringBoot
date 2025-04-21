package org.seba.bll.exceptions.user;

import org.seba.bll.exceptions.GlobalException;
import org.springframework.http.HttpStatus;

public class BadCredentialsException extends GlobalException {
    public BadCredentialsException(HttpStatus status, Object error) {
        super(status, error);
    }
}

