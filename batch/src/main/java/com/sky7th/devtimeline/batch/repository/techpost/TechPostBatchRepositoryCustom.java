package com.sky7th.devtimeline.batch.repository.techpost;

import com.sky7th.devtimeline.core.domain.company.CompanyType;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrlType;
import com.sky7th.devtimeline.core.domain.post.techpost.TechPost;

import java.util.List;

public interface TechPostBatchRepositoryCustom {

    List<TechPost> findByCompanyTypeAndUrlType(CompanyType companyType, CompanyUrlType companyUrlType);


}
