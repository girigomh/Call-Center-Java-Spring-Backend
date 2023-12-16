package org.comcom.exception;

import org.springframework.http.HttpStatus;


public class NotFoundException extends BaseException {
    public NotFoundException(String message, String detail) {
        super(message, detail, HttpStatus.NOT_FOUND.value());
    }
}
