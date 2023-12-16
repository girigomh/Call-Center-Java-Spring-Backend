package org.comcom.exception;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(long userId) {
        super("User not found", String.format("User with id --> " + userId + " not found"));
    }
}
