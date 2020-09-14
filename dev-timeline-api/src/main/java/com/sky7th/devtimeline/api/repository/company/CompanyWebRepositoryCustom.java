package com.sky7th.devtimeline.api.repository.company;

import com.sky7th.devtimeline.core.domain.company.Company;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrlType;

import java.util.List;

public interface CompanyWebRepositoryCustom {

    List<Company> findByCompanyUrlType(CompanyUrlType urlType);
}
