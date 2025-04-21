package org.seba.bll.exceptions.user;

import org.seba.bll.exceptions.GlobalException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends GlobalException {

  public UserNotFoundException(HttpStatus status, Object error) {
    super(status, error);
  }
}
