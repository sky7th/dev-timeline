package com.sky7th.devtimeline.core.domain.techpost;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sky7th.devtimeline.core.domain.company.CompanyType;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrlType;

import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sky7th.devtimeline.core.domain.company.QCompany.company;
import static com.sky7th.devtimeline.core.domain.companyUrl.QCompanyUrl.companyUrl;
import static com.sky7th.devtimeline.core.domain.techpost.QTechPost.techPost;

@RequiredArgsConstructor
public class TechPostRepositoryImpl implements TechPostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<TechPost> findByCompanyTypeAndUrlType(CompanyType companyType, CompanyUrlType companyUrlType) {
        return queryFactory.selectFrom(techPost)
                .leftJoin(companyUrl).on(companyUrl.companyUrlType.eq(companyUrlType))
                .leftJoin(company).on(company.companyType.eq(companyType))
                .fetch();

    }

}
