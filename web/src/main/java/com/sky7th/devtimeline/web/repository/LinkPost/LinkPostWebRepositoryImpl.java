package com.sky7th.devtimeline.web.repository.LinkPost;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sky7th.devtimeline.core.domain.like.PostLike;
import com.sky7th.devtimeline.core.domain.post.linkpost.LinkPost;
import com.sky7th.devtimeline.core.domain.post.linkpost.LinkType;
import com.sky7th.devtimeline.core.domain.post.linkpost.QLinkPost;
import com.sky7th.devtimeline.web.service.dto.LinkPostDto;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.sky7th.devtimeline.core.domain.like.QPostLike.postLike;
import static com.sky7th.devtimeline.core.domain.post.linkpost.QLinkPost.linkPost;

@RequiredArgsConstructor
public class LinkPostWebRepositoryImpl implements LinkPostWebRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<LinkPostDto> findWithLikeCountAndIsLikeByIdAndUserId(Long linkPostid, Long userId) {
        List<LinkPostDto> linkPosts = queryFactory
                .select(
                        Projections.fields(LinkPostDto.class, linkPost,
                                ExpressionUtils.as(
                                        JPAExpressions.select(postLike)
                                                .from(postLike)
                                                .where(postLike.user.id.eq(userId)
                                                .and(postLike.linkPost.id.eq(linkPost.id))),
                                        "isLike"),
                                ExpressionUtils.as(
                                        JPAExpressions.select(postLike.count())
                                                .from(postLike)
                                                .where(postLike.linkPost.id.eq(linkPost.id)),
                                        "likeCount")
                        )
                )
                .from(linkPost)
                .leftJoin(linkPost.user).fetchJoin()
                .leftJoin(linkPost.tags).fetchJoin()
                .where(linkPost.id.eq(linkPostid))
                .fetch();

        return Optional.ofNullable(linkPosts.get(0));
    }

    @Override
    public List<LinkPostDto> findAllWithLikeCountAndIsLikeBySearchForm(PostSearchForm postSearchForm, Long userId) {
        return queryFactory
                .select(
                        Projections.fields(LinkPostDto.class, linkPost,
                                ExpressionUtils.as(
                                        JPAExpressions.select(postLike)
                                                .from(postLike)
                                                .where(postLike.user.id.eq(userId)
                                                        .and(postLike.linkPost.id.eq(linkPost.id))),
                                        "isLike"),
                                ExpressionUtils.as(
                                        JPAExpressions.select(postLike.count())
                                                .from(postLike)
                                                .where(postLike.linkPost.id.eq(linkPost.id)),
                                        "likeCount")
                        )
                )
                .from(linkPost)
                .leftJoin(linkPost.user).fetchJoin()
//                .leftJoin(linkPost.tags).fetchJoin()
                .where(containsTags(postSearchForm.getTags()),
                        inLinkType(postSearchForm.getLinkTypes()))
                .offset(postSearchForm.getOffset())
                .limit(postSearchForm.getLimit())
                .orderBy(linkPost.createdDate.desc())
                .fetch();
    }

//    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
//        Map<Object, Boolean> map = new HashMap<>();
//        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
//    }

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
