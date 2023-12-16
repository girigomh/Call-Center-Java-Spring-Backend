package org.comcom.exception;

import org.springframework.http.HttpStatus;


public class BadInputException extends BaseException {
    public BadInputException(String message, String detail) {
        super(message, detail, HttpStatus.BAD_REQUEST.value());
    }
}
