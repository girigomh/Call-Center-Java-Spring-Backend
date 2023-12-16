package org.comcom.exception;

public class CompanyCategoryNotFoundException extends NotFoundException{

    public CompanyCategoryNotFoundException(long categoryId) {
        super("Company Category Id not found", String.format("Company Category with id --> %d not found", categoryId));
    }
}
