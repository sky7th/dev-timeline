package com.sky7th.devtimeline.core.domain.company.service;

import com.sky7th.devtimeline.core.domain.company.domain.Company;
import com.sky7th.devtimeline.core.domain.company.domain.CompanyUrl;
import com.sky7th.devtimeline.core.domain.company.domain.CompanyUrlRepository;
import com.sky7th.devtimeline.core.domain.company.domain.CompanyUrlType;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CompanyInternalService {

    private final CompanyUrlRepository companyUrlRepository;

    public List<Company> findAllByCompanyUrlType(CompanyUrlType urlType) {
        return companyUrlRepository.findAllByCompanyUrlType(urlType).stream()
            .map(CompanyUrl::getCompany)
            .collect(Collectors.toList());
    }
}
