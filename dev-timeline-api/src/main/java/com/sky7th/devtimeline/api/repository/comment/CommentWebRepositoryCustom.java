package com.sky7th.devtimeline.api.repository.comment;

import com.sky7th.devtimeline.core.domain.comment.Comment;

import java.util.List;

public interface CommentWebRepositoryCustom {
    List<Comment> findFromLastCommentIdToLimit(Long postId, Long lastCommentId, Long limit);
}

