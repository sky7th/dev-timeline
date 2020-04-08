package com.sky7th.devtimeline.web.repository.techPost;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sky7th.devtimeline.core.domain.company.CompanyType;
import com.sky7th.devtimeline.core.domain.techpost.TechPost;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.sky7th.devtimeline.core.domain.techpost.QTechPost.techPost;

@RequiredArgsConstructor
public class TechPostWebRepositoryImpl implements TechPostWebRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<TechPost> findBySearchForm(PostSearchForm postSearchForm) {
        return queryFactory
                .selectFrom(techPost)
                .leftJoin(techPost.companyUrl).fetchJoin()
                .where(containsTags(postSearchForm.getTags()),
                        inCompany(postSearchForm.getCompanies()))
                .offset(postSearchForm.getOffset())
                .limit(postSearchForm.getLimit())
                .orderBy(techPost.sortDate.desc(),
                        techPost.id.desc())
                .fetch();
    }

    @Override
    public long countBySearchForm(PostSearchForm postSearchForm) {
        return queryFactory
                .selectFrom(techPost)
                .leftJoin(techPost.companyUrl).fetchJoin()
                .where(containsTags(postSearchForm.getTags()),
                        inCompany(postSearchForm.getCompanies()))
                .fetchCount();
    }

    private BooleanBuilder containsTags(List<String> tags) {
        if (StringUtils.isEmpty(tags)) {
            return null;
        }
        BooleanBuilder builder = new BooleanBuilder();
        for (String tag : tags) {
            builder.and(techPost.title.contains(tag));
        }
        return builder;
    }

    private BooleanExpression inCompany(List<CompanyType> companies) {
        if (StringUtils.isEmpty(companies)) {
            return null;
        }
        return techPost.companyUrl.company.companyType.in(companies);
    }

}
