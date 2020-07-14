package com.sky7th.devtimeline.web.repository.techPost;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sky7th.devtimeline.core.domain.company.CompanyType;
import com.sky7th.devtimeline.core.domain.post.techpost.TechPost;
import com.sky7th.devtimeline.web.security.UserPrincipal;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;
import com.sky7th.devtimeline.web.service.dto.TechPostItem;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.sky7th.devtimeline.core.domain.like.QPostLike.postLike;
import static com.sky7th.devtimeline.core.domain.post.QPost.post;
import static com.sky7th.devtimeline.core.domain.post.linkpost.QLinkPost.linkPost;
import static com.sky7th.devtimeline.core.domain.post.techpost.QTechPost.techPost;

@RequiredArgsConstructor
public class TechPostWebRepositoryImpl implements TechPostWebRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<TechPostItem> findAllWithLikeCountAndIsLikeBySearchForm(PostSearchForm postSearchForm, UserPrincipal userPrincipal) {
        BooleanExpression likedExpression = getLikedExpression(userPrincipal);
        return queryFactory
                .select(Projections.fields(TechPostItem.class, techPost,
                        ExpressionUtils.as(post.id, "postId"),
                        ExpressionUtils.as(
                                likedExpression,
                                "isLike"),
                        ExpressionUtils.as(
                                JPAExpressions.select(postLike.count())
                                        .from(postLike)
                                        .where(postLike.post.id.eq(post.id)),
                                "likeCount")
                ))
                .from(techPost)
                .leftJoin(post).on(post.crawlId.eq(techPost.postCrawlId))
                .leftJoin(techPost.companyUrl).fetchJoin()
                .where(containsTags(postSearchForm.getTags()),
                        inCompany(postSearchForm.getCompanies()),
                        liked(postSearchForm.isLiked(), likedExpression))
                .offset(postSearchForm.getOffset())
                .limit(postSearchForm.getLimit())
                .orderBy(techPost.sortDate.desc(),
                        techPost.id.desc())
                .fetch();
    }

    @Override
    public long countBySearchForm(PostSearchForm postSearchForm, UserPrincipal userPrincipal) {
        BooleanExpression likedExpression = getLikedExpression(userPrincipal);
        return queryFactory
                .selectFrom(techPost)
                .leftJoin(post).on(post.crawlId.eq(techPost.postCrawlId))
                .leftJoin(techPost.companyUrl).fetchJoin()
                .where(containsTags(postSearchForm.getTags()),
                        inCompany(postSearchForm.getCompanies()),
                        liked(postSearchForm.isLiked(), likedExpression))
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

    private BooleanExpression liked(boolean liked, BooleanExpression likedExpression) {
        if (!liked) {
            return null;
        }
        return likedExpression;
    }

    private BooleanExpression getLikedExpression(UserPrincipal userPrincipal) {
        if (userPrincipal == null) {
            return Expressions.asBoolean(true).isFalse();
        }
        return JPAExpressions.select(postLike)
                .from(postLike)
                .where(postLike.user.id.eq(userPrincipal.getId())
                        .and(postLike.post.id.eq(post.id)))
                .exists();
    }
}
