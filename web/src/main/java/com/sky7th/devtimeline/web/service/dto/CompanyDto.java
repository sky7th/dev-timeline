package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.company.Company;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CompanyDto {

    private String companyName;
    private String companyType;
    private String logoUrl;

    public CompanyDto(Company company) {
        this.companyName = company.getCompanyType().getName();
        this.companyType = company.getCompanyType().toString();
        this.logoUrl = company.getLogoUrl();
    }
}
