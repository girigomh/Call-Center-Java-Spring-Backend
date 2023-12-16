package org.comcom.service.implementation;

import lombok.AllArgsConstructor;
import org.comcom.dto.*;
import org.comcom.exception.BadInputException;
import org.comcom.exception.CompanyCategoryNotFoundException;
import org.comcom.exception.CompanyNotFoundException;
import org.comcom.model.Company;
import org.comcom.model.Users;
import org.comcom.model.Company_Category;
import org.comcom.repository.CompanyCategoryRepository;
import org.comcom.repository.CompanyRepository;
import org.comcom.repository.UserRepository;
import org.comcom.service.CompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyDB;

    private final UserRepository usersDB;

    private final CompanyCategoryRepository categoryDB;

    @Override
    public CompanyDataDto createCompany(CreateCompanyDto createCompanyRequest) {
        Company companyEntity = new Company();
        BeanUtils.copyProperties(createCompanyRequest, companyEntity);
        companyEntity.setCreatedOn(LocalDateTime.now());
        companyEntity.setUpdatedOn(LocalDateTime.now());

        Optional<Company_Category> companyCategory = categoryDB.findById(createCompanyRequest.getCompanyCategoryId());

        if(companyCategory.isPresent()){
            companyEntity.setCompanyCategory(companyCategory.get());
            return companyDB.save(companyEntity).toDto();
        }

        throw new BadInputException("Incorrect Company Category Id", "Company Category Id doesn't  exist");
    }

    @Override
    public List<CompanyDataDto> fetchCompanies() {
        return companyDB.findAll().stream().map(Company::toDto).collect(Collectors.toList());
    }

    @Override
    public CompanyDataDto fetchCompany(Long companyId) {
        if(companyDB.findById(companyId).isPresent()){
            return companyDB.findById(companyId).get().toDto();
        }
        throw new CompanyNotFoundException(companyId);
    }

    @Override
    public CompanyCategoryDataDto createCategory(CompanyCategoryDto createCompanyCategory) {
        return categoryDB.save(new Company_Category(createCompanyCategory.getName(), createCompanyCategory.getDescription())).toDto();
    }

    @Override
    public List<CompanyCategoryDataDto> fetchCategories() {
        return categoryDB.findAll().stream().map(Company_Category::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Users> getCompanyEmployees(Long id) {
        return companyDB.findById(id).get().getUsers();
    }

    @Override
    public boolean deleteCompany(Long companyId) {
        if(companyDB.findById(companyId).isPresent()){
            companyDB.deleteById(companyId);
            return true;
        }else {
            throw new CompanyNotFoundException(companyId);
        }
    }

    @Override
    public boolean deleteCompanyCategory(Long categoryId) {
        if(categoryDB.findById(categoryId).isPresent()){
            categoryDB.deleteById(categoryId);
            return true;
        }else {
            throw new CompanyCategoryNotFoundException(categoryId);
        }
    }
}
