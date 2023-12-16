package org.comcom.service;

import org.comcom.dto.*;
import org.comcom.model.Users;

import java.util.List;

public interface CompanyService {

    CompanyDataDto createCompany(CreateCompanyDto createCompanyRequest);

    List<CompanyDataDto> fetchCompanies();

    CompanyDataDto fetchCompany(Long companyId);

    CompanyCategoryDataDto createCategory(CompanyCategoryDto createCompanyCategory);

    List<Users> getCompanyEmployees(Long companyId);

    List<CompanyCategoryDataDto> fetchCategories();

    boolean deleteCompany(Long companyId);

    boolean deleteCompanyCategory(Long categoryId);

}
