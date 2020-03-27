package com.sky7th.devtimeline.batch.service;

import com.sky7th.devtimeline.batch.domain.company.Company;
import com.sky7th.devtimeline.batch.domain.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }
}
