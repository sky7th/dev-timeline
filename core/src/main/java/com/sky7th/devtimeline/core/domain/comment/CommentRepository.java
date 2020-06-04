package com.sky7th.devtimeline.core.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteByLinkPostIdAndUserId(Long linkPostId, Long userId);
}
