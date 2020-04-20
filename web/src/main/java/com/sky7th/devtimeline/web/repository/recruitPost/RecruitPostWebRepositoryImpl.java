package com.sky7th.devtimeline.web.repository.recruitPost;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sky7th.devtimeline.core.domain.company.CompanyType;
import com.sky7th.devtimeline.core.domain.post.recruitpost.RecruitPost;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.sky7th.devtimeline.core.domain.post.recruitpost.QRecruitPost.recruitPost;

@RequiredArgsConstructor
public class RecruitPostWebRepositoryImpl implements RecruitPostWebRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<RecruitPost> findBySearchForm(PostSearchForm postSearchForm) {
        return queryFactory
                .selectFrom(recruitPost)
                .leftJoin(recruitPost.companyUrl).fetchJoin()
                .where(containsTags(postSearchForm.getTags()),
                        inCompany(postSearchForm.getCompanies()))
                .offset(postSearchForm.getOffset())
                .limit(postSearchForm.getLimit())
                .orderBy(recruitPost.sortDate.desc(),
                        recruitPost.id.desc())
                .fetch();
    }

    @Override
    public long countBySearchForm(PostSearchForm postSearchForm) {
        return queryFactory
                .selectFrom(recruitPost)
                .leftJoin(recruitPost.companyUrl).fetchJoin()
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
            builder.and(recruitPost.title.contains(tag));
        }
        return builder;
    }

    private BooleanExpression inCompany(List<CompanyType> companies) {
        if (StringUtils.isEmpty(companies)) {
            return null;
        }
        return recruitPost.companyUrl.company.companyType.in(companies);
    }

}
