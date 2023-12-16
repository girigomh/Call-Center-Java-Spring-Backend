package org.comcom.exception;

import org.springframework.http.HttpStatus;


public class UnauthorizedException extends BaseException {
    public UnauthorizedException(String message, String detail) {
        super(message, detail, HttpStatus.UNAUTHORIZED.value());
    }
}
