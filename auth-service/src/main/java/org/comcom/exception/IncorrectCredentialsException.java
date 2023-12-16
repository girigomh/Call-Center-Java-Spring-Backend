package org.comcom.exception;

public class IncorrectCredentialsException extends UnauthorizedException {
    public IncorrectCredentialsException() {
        super("Incorrect credentials", "Incorrect credentials");
    }
}
