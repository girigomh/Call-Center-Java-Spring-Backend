package org.comcom.exception;

public class AddressNotFoundException extends NotFoundException {
    public AddressNotFoundException(Long addressId) {
        super("Address not found", String.format("Address with id %d was not found", addressId));
    }
}
