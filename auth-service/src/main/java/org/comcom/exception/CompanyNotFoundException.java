package org.comcom.exception;

public class CompanyNotFoundException extends NotFoundException {
    public CompanyNotFoundException(long companyId) {
        super("Company Id not found", String.format("Company with id --> " + companyId + " not found"));
    }
}
