package com.sky7th.devtimeline.core.domain.company.service;

import com.sky7th.devtimeline.core.domain.company.domain.Company;
import com.sky7th.devtimeline.core.domain.company.domain.CompanyRepository;
import com.sky7th.devtimeline.core.domain.company.domain.CompanyUrlType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CompanyInternalService {

    private final CompanyRepository companyRepository;

    public List<Company> findAllByCompanyUrlType(CompanyUrlType urlType) {
        return companyRepository.findAllByCompanyUrlType(urlType);
    }
}
