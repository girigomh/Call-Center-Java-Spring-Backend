package org.comcom.exception;

public class VerifyNotException extends UnauthorizedException {
    public VerifyNotException() {
        super("Your email has not been confirmed.", "Your email has not been confirmed.");
    }
}


