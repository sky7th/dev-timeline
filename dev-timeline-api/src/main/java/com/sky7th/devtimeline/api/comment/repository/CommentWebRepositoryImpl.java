package com.sky7th.devtimeline.api.comment.repository;

import static com.sky7th.devtimeline.core.domain.comment.domain.QComment.comment;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sky7th.devtimeline.core.domain.comment.domain.Comment;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentWebRepositoryImpl implements CommentWebRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Comment> findFromLastCommentIdToLimit(Long postId, Long lastCommentId, Long limit) {
        return queryFactory.selectFrom(comment)
                .leftJoin(comment.user).fetchJoin()
                .where(comment.post.id.eq(postId))
                .where(lessThanCommentId(lastCommentId))
                .offset(0)
                .limit(limit)
                .orderBy(comment.createdDate.desc())
                .fetch();
    }

    private BooleanBuilder lessThanCommentId(Long lastCommentId) {
        if (lastCommentId == null) {
            return null;
        }
        return new BooleanBuilder().and(comment.id.lt(lastCommentId));
    }
}

