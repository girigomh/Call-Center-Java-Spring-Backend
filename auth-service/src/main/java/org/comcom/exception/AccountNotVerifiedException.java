package org.comcom.exception;

public class AccountNotVerifiedException extends UnauthorizedException {
    public AccountNotVerifiedException() {
        super("Account is not verified", "Account is not verified");
    }
}
