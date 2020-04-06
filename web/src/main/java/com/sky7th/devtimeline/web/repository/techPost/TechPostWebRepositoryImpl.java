package com.sky7th.devtimeline.web.repository.techPost;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sky7th.devtimeline.core.domain.company.CompanyType;
import com.sky7th.devtimeline.core.domain.techpost.TechPost;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.sky7th.devtimeline.core.domain.recruitpost.QRecruitPost.recruitPost;
import static com.sky7th.devtimeline.core.domain.techpost.QTechPost.techPost;

@RequiredArgsConstructor
public class TechPostWebRepositoryImpl implements TechPostWebRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<TechPost> findBySearchForm(PostSearchForm postSearchForm) {
        return queryFactory
                .selectFrom(techPost)
                .leftJoin(techPost.companyUrl).fetchJoin()
                .where(containsTitle(postSearchForm.getTitle()),
                        inCompany(postSearchForm.getCompanies()))
                .offset(postSearchForm.getOffset())
                .limit(postSearchForm.getLimit())
                .orderBy(techPost.sortDate.desc())
                .fetch();
    }

    private BooleanExpression containsTitle(String title) {
        if (StringUtils.isEmpty(title)) {
            return null;
        }
        return techPost.title.contains(title);
    }

    private BooleanExpression inCompany(List<CompanyType> companies) {
        if (StringUtils.isEmpty(companies)) {
            return null;
        }
        return recruitPost.companyUrl.company.companyType.in(companies);
    }

}
