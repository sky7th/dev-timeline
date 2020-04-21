package com.sky7th.devtimeline.web.repository.Like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.sky7th.devtimeline.core.domain.like.QPostLike.postLike;

@RequiredArgsConstructor
public class PostLikeWebRepositoryImpl implements PostLikeWebRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteByLinkPostAndUser(Long linkPostId, Long userId) {
        queryFactory.delete(postLike)
                .where(postLike.linkPost.id.eq(linkPostId)
                        .and(postLike.user.id.eq(userId)))
                .execute();
    }
}
