package com.sky7th.devtimeline.batch.repository.recruitpost;

import com.sky7th.devtimeline.core.domain.company.CompanyType;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrlType;
import com.sky7th.devtimeline.core.domain.recruitpost.RecruitPost;

import java.util.List;

public interface RecruitPostBatchRepositoryCustom {

    List<RecruitPost> findByCompanyTypeAndUrlType(CompanyType companyType, CompanyUrlType companyUrlType);

}
