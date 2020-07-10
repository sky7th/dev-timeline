package com.sky7th.devtimeline.batch.dto;

import com.sky7th.devtimeline.core.domain.company.Company;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private Company company;
    private CompanyUrl companyUrl;
}
