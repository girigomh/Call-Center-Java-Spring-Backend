package org.comcom.exception;

import org.springframework.http.HttpStatus;


public class ForbiddenException extends BaseException {
    public ForbiddenException(String message, String detail) {
        super(message, detail, HttpStatus.FORBIDDEN.value());
    }
}
