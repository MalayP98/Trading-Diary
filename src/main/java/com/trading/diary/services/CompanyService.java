package com.trading.diary.services;

import com.trading.diary.exception.CompanyNotFoundException;
import com.trading.diary.pojo.Company;
import com.trading.diary.repositories.miscs.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Company addCompany(String companySymbol){
        return companyRepository.save(new Company(companySymbol.toUpperCase()));
    }

    public Company getCompany(String companySymbol) throws CompanyNotFoundException {
        return companyRepository.findById(companySymbol)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found!"));
    }

    public Company getOrCreateCompany(String companySymbol){
        try{
            return getCompany(companySymbol);
        } catch (CompanyNotFoundException e){
            return addCompany(companySymbol);
        }
    }
}
