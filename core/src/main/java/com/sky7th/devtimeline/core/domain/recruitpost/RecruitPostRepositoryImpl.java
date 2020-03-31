package com.sky7th.devtimeline.core.domain.recruitpost;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sky7th.devtimeline.core.domain.company.CompanyType;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrlType;

import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sky7th.devtimeline.core.domain.recruitpost.QRecruitPost.recruitPost;
import static com.sky7th.devtimeline.core.domain.companyUrl.QCompanyUrl.companyUrl;
import static com.sky7th.devtimeline.core.domain.company.QCompany.company;

@RequiredArgsConstructor
public class RecruitPostRepositoryImpl implements RecruitPostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<RecruitPost> findByCompanyTypeAndUrlType(CompanyType companyType, CompanyUrlType companyUrlType) {
        return queryFactory.selectFrom(recruitPost)
                .leftJoin(companyUrl).on(companyUrl.companyUrlType.eq(companyUrlType))
                .leftJoin(company).on(company.companyType.eq(companyType))
                .fetch();

    }

}
