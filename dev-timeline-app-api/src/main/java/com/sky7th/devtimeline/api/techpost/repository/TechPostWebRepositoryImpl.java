package com.sky7th.devtimeline.api.techpost.repository;

import static com.sky7th.devtimeline.crawlpost.recruitpost.domain.QRecruitPost.recruitPost;
import static com.sky7th.devtimeline.crawlpost.techpost.domain.QTechPost.techPost;
import static com.sky7th.devtimeline.crawlpost.company.domain.QCompanyUrl.companyUrl;
import static com.sky7th.devtimeline.crawlpost.company.domain.QCompany.company;
import static com.sky7th.devtimeline.post.post.domain.QPost.post;
import static com.sky7th.devtimeline.post.postlike.domain.QPostLike.postLike;
import static java.util.Objects.isNull;

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
import com.sky7th.devtimeline.crawlpost.CrawlPostSearchForm;
import com.sky7th.devtimeline.crawlpost.company.domain.CompanyType;
import com.sky7th.devtimeline.crawlpost.techpost.dto.TechPostItem;
import com.sky7th.devtimeline.post.post.dto.OrderType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class TechPostWebRepositoryImpl implements TechPostWebRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private NumberPath<Long> likeCount = Expressions.numberPath(Long.class, "likeCount");


    @Override
    public List<TechPostItem> findAllWithLikeCountAndIsLikeBySearchForm(
        CrawlPostSearchForm postSearchForm) {
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
                        inCompany(postSearchForm.getCompany()),
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
    public long countBySearchForm(CrawlPostSearchForm postSearchForm) {
        return queryFactory
                .selectFrom(techPost)
                .leftJoin(post).on(post.crawlId.eq(techPost.postCrawlId))
                .leftJoin(techPost.companyUrl, companyUrl).fetchJoin()
                .leftJoin(companyUrl.company, company).fetchJoin()
                .where(containsTags(postSearchForm.getTags()),
                        inCompany(postSearchForm.getCompany()),
                        liked(postSearchForm.isLiked()))
                .fetchCount();
    }

    private BooleanBuilder containsTags(List<String> tags) {
        if (StringUtils.isEmpty(tags)) {
            return null;
        }
        BooleanBuilder builder = new BooleanBuilder();
        for (String tag : tags) {
            builder.or(techPost.title.contains(tag));
        }
        return builder;
    }

    private BooleanExpression inCompany(CompanyType companyType) {
        if (companyType == CompanyType.ALL || isNull(companyType)) {
            return null;
        }
        return techPost.companyUrl.company.companyType.eq(companyType);
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
                .where(postLike.userId.eq(SecurityContextSupport.getUserContext().getId())
                        .and(postLike.post.id.eq(post.id)))
                .exists();
    }
}
