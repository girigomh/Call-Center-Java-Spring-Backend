package org.comcom.exception;

import org.springframework.http.HttpStatus;


public class UnavailableException extends BaseException {
    public UnavailableException(String message, String detail) {
        super(message, detail, HttpStatus.SERVICE_UNAVAILABLE.value());
    }
}
