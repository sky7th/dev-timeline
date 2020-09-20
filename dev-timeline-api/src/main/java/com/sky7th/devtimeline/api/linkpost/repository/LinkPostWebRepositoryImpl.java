package com.sky7th.devtimeline.api.linkpost.repository;

import static com.sky7th.devtimeline.core.domain.comment.domain.QComment.comment;
import static com.sky7th.devtimeline.core.domain.linkpost.domain.QLinkPost.linkPost;
import static com.sky7th.devtimeline.core.domain.postlike.domain.QPostLike.postLike;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sky7th.devtimeline.core.domain.linkpost.domain.LinkType;
import com.sky7th.devtimeline.core.domain.linkpost.dto.LinkPostItem;
import com.sky7th.devtimeline.core.domain.post.dto.PostSearchForm;
import com.sky7th.devtimeline.core.domain.post.dto.SortOrderType;
import com.sky7th.devtimeline.core.domain.user.dto.UserContext;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class LinkPostWebRepositoryImpl implements LinkPostWebRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private NumberPath<Long> likeCount = Expressions.numberPath(Long.class, "likeCount");

    @Override
    public Optional<LinkPostItem> findWithLikeCountAndIsLikeByIdAndUserId(Long postId, UserContext userContext) {
        BooleanExpression likedExpression = getLikedExpression(userContext);
        List<LinkPostItem> linkPosts = queryFactory
                .select(
                    Projections.fields(LinkPostItem.class, linkPost,
                        ExpressionUtils.as(
                                likedExpression,
                            "isLike"),
                        ExpressionUtils.as(
                            JPAExpressions.select(postLike.count())
                                .from(postLike)
                                .where(postLike.post.id.eq(linkPost.post.id)),
                            "likeCount"),
                        ExpressionUtils.as(
                            JPAExpressions.select(comment.count())
                                .from(comment)
                                .where(comment.post.id.eq(linkPost.post.id)),
                            "commentCount")
                    )
                )
                .from(linkPost)
                .leftJoin(linkPost.post).fetchJoin()
                .leftJoin(linkPost.user).fetchJoin()
                .where(linkPost.post.id.eq(postId))
                .fetch();

        return Optional.ofNullable(linkPosts.get(0));
    }

    @Override
    public List<LinkPostItem> findAllWithLikeCountAndIsLikeBySearchForm(PostSearchForm postSearchForm, UserContext userContext) {
        BooleanExpression likedExpression = getLikedExpression(userContext);
        return queryFactory
                .select(
                    Projections.fields(LinkPostItem.class, linkPost,
                        ExpressionUtils.as(
                                likedExpression,
                            "isLike"),
                        ExpressionUtils.as(
                            JPAExpressions.select(postLike.count())
                                .from(postLike)
                                .where(postLike.post.id.eq(linkPost.post.id)),
                                likeCount),
                        ExpressionUtils.as(
                            JPAExpressions.select(comment.count())
                                .from(comment)
                                .where(comment.post.id.eq(linkPost.post.id)),
                            "commentCount")
                    )
                )
                .from(linkPost)
                .leftJoin(linkPost.post).fetchJoin()
                .leftJoin(linkPost.user).fetchJoin()
                .where(containsTags(postSearchForm.getTags()),
                        inLinkType(postSearchForm.getLinkTypes()),
                        liked(postSearchForm.isLiked(), likedExpression))
                .offset(postSearchForm.getOffset())
                .limit(postSearchForm.getLimit())
                .orderBy(sortOrder(SortOrderType.valueOf(postSearchForm.getSortOrderType())))
                .fetch();
    }

    private OrderSpecifier sortOrder(SortOrderType sortOrderType) {
        if (sortOrderType == SortOrderType.ASC) {
            return linkPost.createdDate.asc();
        } else if (sortOrderType == SortOrderType.LIKE) {
            return likeCount.desc();
        }
        return linkPost.createdDate.desc();
    }

//    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
//        Map<Object, Boolean> map = new HashMap<>();
//        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
//    }

    @Override
    public long countBySearchForm(PostSearchForm postSearchForm, UserContext userContext) {
        BooleanExpression likedExpression = getLikedExpression(userContext);
        return queryFactory
                .selectFrom(linkPost)
                .where(containsTags(postSearchForm.getTags()),
                        liked(postSearchForm.isLiked(), likedExpression))
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
                        .and(postLike.post.id.eq(linkPost.post.id)))
                .exists();
    }
}
