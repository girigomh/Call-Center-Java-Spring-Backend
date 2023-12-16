package org.comcom.exception;

import org.springframework.http.HttpStatus;


public class ConflictException extends BaseException {
    public ConflictException(String message, String detail) {
        super(message, detail, HttpStatus.CONFLICT.value());
    }
}
