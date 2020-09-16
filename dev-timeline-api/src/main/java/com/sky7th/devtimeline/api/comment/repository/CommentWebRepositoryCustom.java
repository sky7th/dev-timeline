package com.sky7th.devtimeline.api.comment.repository;

import com.sky7th.devtimeline.core.domain.comment.domain.Comment;

import java.util.List;

public interface CommentWebRepositoryCustom {
    List<Comment> findFromLastCommentIdToLimit(Long postId, Long lastCommentId, Long limit);
}

