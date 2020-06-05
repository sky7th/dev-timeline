package com.sky7th.devtimeline.web.repository.comment;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sky7th.devtimeline.core.domain.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.h2.util.StringUtils;

import java.util.List;

import static com.sky7th.devtimeline.core.domain.comment.QComment.comment;

@RequiredArgsConstructor
public class CommentWebRepositoryImpl implements CommentWebRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Comment> findFromLastCommentIdToLimit(Long postId, Long lastCommentId, Long limit) {
        return queryFactory.selectFrom(comment)
                .where(comment.linkPost.id.eq(postId))
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

