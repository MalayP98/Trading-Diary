package com.trading.diary.services;

import com.trading.diary.exception.CompanyNotFoundException;
import com.trading.diary.pojo.Company;
import com.trading.diary.repositories.miscs.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Application service for normalizing and resolving company symbols before trades are persisted.
 */
@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    /**
     * Creates a company record after normalizing the symbol to upper case.
     */
    public Company addCompany(String companySymbol){
        return companyRepository.save(new Company(companySymbol.toUpperCase()));
    }

    public Company getCompany(String companySymbol) throws CompanyNotFoundException {
        return companyRepository.findById(companySymbol)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found!"));
    }

    /**
     * Returns an existing company when present or creates it on demand so trade entry flows can stay single-pass.
     */
    public Company getOrCreateCompany(String companySymbol){
        try{
            return getCompany(companySymbol);
        } catch (CompanyNotFoundException e){
            return addCompany(companySymbol);
        }
    }
}
