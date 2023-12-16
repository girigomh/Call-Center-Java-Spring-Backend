package org.comcom.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException {
    private String message;
    private String detail;
    private int statusCode;

    public BaseException(String message, String detail, int statusCode) {
        super(detail);
        this.message = message;
        this.detail = detail;
        this.statusCode = statusCode;
    }
}
