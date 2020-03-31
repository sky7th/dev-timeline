package com.sky7th.devtimeline.core.domain.techpost;

import com.sky7th.devtimeline.core.domain.company.CompanyType;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrlType;

import java.util.List;

public interface TechPostRepositoryCustom {

    List<TechPost> findByCompanyTypeAndUrlType(CompanyType companyType, CompanyUrlType companyUrlType);


}
