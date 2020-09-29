package com.sky7th.devtimeline.core.domain.postlike.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

  @Query(value = "SELECT l FROM PostLike l JOIN FETCH l.user JOIN FETCH l.post WHERE l.post.id = :postId AND l.user.id = :userId")
  Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId);
}
