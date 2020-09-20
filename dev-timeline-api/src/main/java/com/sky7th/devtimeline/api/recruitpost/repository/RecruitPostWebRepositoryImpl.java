package com.sky7th.devtimeline.api.recruitpost.repository;

import static com.sky7th.devtimeline.core.domain.post.domain.QPost.post;
import static com.sky7th.devtimeline.core.domain.postlike.domain.QPostLike.postLike;
import static com.sky7th.devtimeline.core.domain.recruitpost.domain.QRecruitPost.recruitPost;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sky7th.devtimeline.core.domain.company.domain.CompanyType;
import com.sky7th.devtimeline.core.domain.post.dto.PostSearchForm;
import com.sky7th.devtimeline.core.domain.post.dto.SortOrderType;
import com.sky7th.devtimeline.core.domain.recruitpost.dto.RecruitPostItem;
import com.sky7th.devtimeline.core.domain.user.dto.UserContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class RecruitPostWebRepositoryImpl implements RecruitPostWebRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private NumberPath<Long> likeCount = Expressions.numberPath(Long.class, "likeCount");

    @Override
    public List<RecruitPostItem> findAllWithLikeCountAndIsLikeBySearchForm(PostSearchForm postSearchForm, UserContext userContext) {
        BooleanExpression likedExpression = getLikedExpression(userContext);
        return queryFactory
                .select(Projections.fields(RecruitPostItem.class, recruitPost,
                        ExpressionUtils.as(post.id, "postId"),
                        ExpressionUtils.as(
                                likedExpression,
                                "isLike"),
                        ExpressionUtils.as(
                                JPAExpressions.select(postLike.count())
                                        .from(postLike)
                                        .where(postLike.post.id.eq(post.id)),
                                likeCount)
                ))
                .from(recruitPost)
                .leftJoin(post).on(post.crawlId.eq(recruitPost.postCrawlId))
                .where(containsTags(postSearchForm.getTags()),
                        inCompany(postSearchForm.getCompanies()),
                        liked(postSearchForm.isLiked(), likedExpression))
                .offset(postSearchForm.getOffset())
                .limit(postSearchForm.getLimit())
                .orderBy(sortOrder(SortOrderType.valueOf(postSearchForm.getSortOrderType())),
                        recruitPost.id.desc())
                .fetch();
    }

    private OrderSpecifier sortOrder(SortOrderType sortOrderType) {
        if (sortOrderType == SortOrderType.ASC) {
            return recruitPost.sortDate.asc();
        } else if (sortOrderType == SortOrderType.LIKE) {
            return likeCount.desc();
        }
        return recruitPost.sortDate.desc();
    }

    @Override
    public long countBySearchForm(PostSearchForm postSearchForm, UserContext userContext) {
        BooleanExpression likedExpression = getLikedExpression(userContext);
        return queryFactory
                .selectFrom(recruitPost)
                .leftJoin(post).on(post.crawlId.eq(recruitPost.postCrawlId))
                .leftJoin(recruitPost.companyUrl).fetchJoin()
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

    private BooleanExpression liked(boolean liked, BooleanExpression likedExpression) {
        if (!liked) {
            return null;
        }
        return likedExpression;
    }

    private BooleanExpression getLikedExpression(UserContext userContext) {
        if (userContext.getId() == null) {
            return Expressions.asBoolean(true).isFalse();
        }
        return JPAExpressions.select(postLike)
                .from(postLike)
                .where(postLike.user.id.eq(userContext.getId())
                        .and(postLike.post.id.eq(post.id)))
                .exists();
    }
}
