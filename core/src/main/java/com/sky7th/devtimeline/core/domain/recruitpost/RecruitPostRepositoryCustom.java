package com.sky7th.devtimeline.core.domain.recruitpost;

import com.sky7th.devtimeline.core.domain.company.CompanyType;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrlType;

import java.util.List;

public interface RecruitPostRepositoryCustom {

    List<RecruitPost> findByCompanyTypeAndUrlType(CompanyType companyType, CompanyUrlType companyUrlType);

}
