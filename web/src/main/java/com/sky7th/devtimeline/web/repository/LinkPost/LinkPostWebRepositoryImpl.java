package com.sky7th.devtimeline.web.repository.LinkPost;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sky7th.devtimeline.core.domain.post.linkpost.LinkPost;
import com.sky7th.devtimeline.core.domain.post.linkpost.LinkType;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.sky7th.devtimeline.core.domain.post.linkpost.QLinkPost.linkPost;

@RequiredArgsConstructor
public class LinkPostWebRepositoryImpl implements LinkPostWebRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<LinkPost> findBySearchForm(PostSearchForm postSearchForm) {
        return queryFactory
                .selectFrom(linkPost)
                .leftJoin(linkPost.user).fetchJoin()
                .leftJoin(linkPost.tags).fetchJoin()
                .where(containsTags(postSearchForm.getTags()),
                        inLinkType(postSearchForm.getLinkTypes()))
                .offset(postSearchForm.getOffset())
                .limit(postSearchForm.getLimit())
                .orderBy(linkPost.createdDate.desc())
                .fetch();
    }

    @Override
    public long countBySearchForm(PostSearchForm postSearchForm) {
        return queryFactory
                .selectFrom(linkPost)
                .where(containsTags(postSearchForm.getTags()))
                .fetchCount();
    }

    private BooleanBuilder containsTags(List<String> tags) {
        if (StringUtils.isEmpty(tags)) {
            return null;
        }
        BooleanBuilder builder = new BooleanBuilder();
        for (String tag : tags) {
            builder.and(linkPost.title.contains(tag));
        }
        return builder;
    }

    private BooleanExpression inLinkType(List<LinkType> linkTypes) {
        if (StringUtils.isEmpty(linkTypes)) {
            return null;
        }
        return linkPost.linkType.in(linkTypes);
    }

}
