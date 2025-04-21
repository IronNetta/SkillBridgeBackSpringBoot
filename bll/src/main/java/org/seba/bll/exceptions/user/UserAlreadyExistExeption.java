package org.seba.bll.exceptions.user;

import org.seba.bll.exceptions.GlobalException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistExeption extends GlobalException {
    public UserAlreadyExistExeption(HttpStatus status, Object error) {
        super(status, error);
    }
}
