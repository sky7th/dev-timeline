package com.sky7th.devtimeline.api.postlike.repository;

import static com.sky7th.devtimeline.post.postlike.domain.QPostLike.postLike;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class PostLikeWebRepositoryImpl implements PostLikeWebRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteByPostIdAndUserId(Long postId, Long userId) {
        queryFactory.delete(postLike)
                .where(postLike.post.id.eq(postId)
                        .and(postLike.userId.eq(userId)))
                .execute();
    }
}
