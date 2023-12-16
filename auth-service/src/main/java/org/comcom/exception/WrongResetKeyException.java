package org.comcom.exception;

public class WrongResetKeyException extends BadInputException {
    public WrongResetKeyException(int resetKey) {
        super("Incorrect or expired reset key", String.format("Reset key %d is either invalid or expired", resetKey));
    }
}
