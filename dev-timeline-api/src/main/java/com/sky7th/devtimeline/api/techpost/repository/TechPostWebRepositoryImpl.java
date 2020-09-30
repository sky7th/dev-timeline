package com.sky7th.devtimeline.api.techpost.repository;

import static com.sky7th.devtimeline.core.domain.company.domain.QCompany.company;
import static com.sky7th.devtimeline.core.domain.company.domain.QCompanyUrl.companyUrl;
import static com.sky7th.devtimeline.core.domain.post.domain.QPost.post;
import static com.sky7th.devtimeline.core.domain.postlike.domain.QPostLike.postLike;
import static com.sky7th.devtimeline.core.domain.techpost.domain.QTechPost.techPost;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sky7th.devtimeline.api.security.SecurityContextSupport;
import com.sky7th.devtimeline.core.domain.company.domain.CompanyType;
import com.sky7th.devtimeline.core.domain.post.dto.PostSearchForm;
import com.sky7th.devtimeline.core.domain.post.dto.PostSearchForm.OrderType;
import com.sky7th.devtimeline.core.domain.techpost.dto.TechPostItem;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class TechPostWebRepositoryImpl implements TechPostWebRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private NumberPath<Long> likeCount = Expressions.numberPath(Long.class, "likeCount");


    @Override
    public List<TechPostItem> findAllWithLikeCountAndIsLikeBySearchForm(PostSearchForm postSearchForm) {
        return queryFactory
                .select(Projections.fields(TechPostItem.class, techPost,
                        ExpressionUtils.as(post.id, "postId"),
                        ExpressionUtils.as(post.likeCount, "likeCount"),
                        ExpressionUtils.as(getLikedExpression(), "isLike")
                ))
                .from(techPost)
                .leftJoin(post).on(post.crawlId.eq(techPost.postCrawlId))
                .leftJoin(techPost.companyUrl, companyUrl).fetchJoin()
                .leftJoin(companyUrl.company, company).fetchJoin()
                .where(containsTags(postSearchForm.getTags()),
                        inCompany(postSearchForm.getCompanies()),
                        liked(postSearchForm.isLiked()))
                .offset(postSearchForm.getOffset())
                .limit(postSearchForm.getLimit())
                .orderBy(sortOrder(postSearchForm.getOrderType()))
                .fetch();
    }

    private OrderSpecifier[] sortOrder(OrderType orderType) {
        if (orderType == OrderType.ASC) {
            return new OrderSpecifier[] {techPost.sortDate.asc()};

        } else if (orderType == OrderType.LIKE) {
            return new OrderSpecifier[] {likeCount.desc(), techPost.sortDate.desc()};
        }

        return new OrderSpecifier[] {techPost.sortDate.desc()};
    }

    @Override
    public long countBySearchForm(PostSearchForm postSearchForm) {
        return queryFactory
                .selectFrom(techPost)
                .leftJoin(post).on(post.crawlId.eq(techPost.postCrawlId))
                .leftJoin(techPost.companyUrl, companyUrl).fetchJoin()
                .leftJoin(companyUrl.company, company).fetchJoin()
                .where(containsTags(postSearchForm.getTags()),
                        inCompany(postSearchForm.getCompanies()),
                        liked(postSearchForm.isLiked()))
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

    private BooleanExpression liked(boolean liked) {
        if (!liked) {
            return null;
        }
        return getLikedExpression();
    }

    private BooleanExpression getLikedExpression() {
        if (SecurityContextSupport.isNotLogined()) {
            return Expressions.asBoolean(true).isFalse();
        }

        return JPAExpressions.select(postLike)
                .from(postLike)
                .where(postLike.user.id.eq(SecurityContextSupport.getUserContext().getId())
                        .and(postLike.post.id.eq(post.id)))
                .exists();
    }
}
