package com.sky7th.devtimeline.web.repository.techPost;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sky7th.devtimeline.core.domain.techpost.TechPost;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sky7th.devtimeline.core.domain.techpost.QTechPost.techPost;

@RequiredArgsConstructor
public class TechPostWebRepositoryImpl implements TechPostWebRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<TechPost> findAllLimitDesc(long offset, long limit) {
        return queryFactory
                .selectFrom(techPost)
                .leftJoin(techPost.companyUrl).fetchJoin()
                .offset(offset)
                .limit(limit)
                .orderBy(techPost.sortDate.desc())
                .fetch();
    }

    @Override
    public List<TechPost> findByTitleContainingLimitDesc(String title, long offset, long limit) {
        return queryFactory
                .selectFrom(techPost)
                .leftJoin(techPost.companyUrl).fetchJoin()
                .where(techPost.title.contains(title))
                .offset(offset)
                .limit(limit)
                .orderBy(techPost.sortDate.desc())
                .fetch();
    }
}
