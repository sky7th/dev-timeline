package com.sky7th.devtimeline.web.repository.recruitPost;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sky7th.devtimeline.core.domain.recruitpost.RecruitPost;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sky7th.devtimeline.core.domain.recruitpost.QRecruitPost.recruitPost;

@RequiredArgsConstructor
public class RecruitPostWebRepositoryImpl implements RecruitPostWebRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<RecruitPost> findAllLimitDesc(long offset, long limit) {
        return queryFactory
                .selectFrom(recruitPost)
                .leftJoin(recruitPost.companyUrl).fetchJoin()
                .leftJoin(recruitPost.companyUrl.company).fetchJoin()
                .offset(offset)
                .limit(limit)
                .orderBy(recruitPost.sortDate.desc())
                .fetch();
    }
}
