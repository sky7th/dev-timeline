package com.sky7th.devtimeline.core.domain.postlike.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

  @Query(value = "SELECT count(p) > 0 FROM post_like p WHERE p.post_id = :postId/ AND p.user_id = :userId", nativeQuery = true)
  Boolean existsByPostIdAndUserId(Long postId, Long userId);

  @Query(value = "SELECT p FROM post_like p LEFT JOIN user u ON u.id = p.user_id WHERE p.post_id = :postId AND p.user_id = :userId", nativeQuery = true)
  Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId);
}
