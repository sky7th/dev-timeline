package com.sky7th.devtimeline.web.repository.company;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sky7th.devtimeline.core.domain.company.Company;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrlType;
import com.sky7th.devtimeline.web.service.dto.CompanyDto;
import lombok.RequiredArgsConstructor;

import java.util.List;
import static com.sky7th.devtimeline.core.domain.company.QCompany.company;
import static com.sky7th.devtimeline.core.domain.companyUrl.QCompanyUrl.companyUrl;

@RequiredArgsConstructor
public class CompanyWebRepositoryImpl implements CompanyWebRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Company> findByCompanyUrlType(CompanyUrlType urlType) {
        return queryFactory.selectFrom(company)
            .leftJoin(companyUrl).on(companyUrl.company.eq(company))
            .where(companyUrl.companyUrlType.eq(urlType))
            .fetch();
    }
}
