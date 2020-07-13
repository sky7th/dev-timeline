package com.sky7th.devtimeline.web.repository.Like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.sky7th.devtimeline.core.domain.like.QPostLike.postLike;

@RequiredArgsConstructor
public class PostLikeWebRepositoryImpl implements PostLikeWebRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteByPostIdAndUserId(Long postId, Long userId) {
        queryFactory.delete(postLike)
                .where(postLike.post.id.eq(postId)
                        .and(postLike.user.id.eq(userId)))
                .execute();
    }
}
