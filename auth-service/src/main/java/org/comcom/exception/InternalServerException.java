package org.comcom.exception;

import org.springframework.http.HttpStatus;


public class InternalServerException extends BaseException {
    public InternalServerException(String message, String detail) {
        super(message, detail, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
