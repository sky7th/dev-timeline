package com.sky7th.devtimeline.api.company.service;

import com.sky7th.devtimeline.core.domain.company.dto.CompanyResponseDtos;
import com.sky7th.devtimeline.core.domain.company.domain.CompanyUrlType;
import com.sky7th.devtimeline.core.domain.company.service.CompanyInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyInternalService companyInternalService;

    public CompanyResponseDtos findAllByCompanyUrlType(CompanyUrlType urlType) {
        return CompanyResponseDtos.of(companyInternalService.findAllByCompanyUrlType(urlType));
    }
}
