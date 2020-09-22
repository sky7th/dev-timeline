package com.sky7th.devtimeline.core.domain.linkpost.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LinkPostRepository extends JpaRepository<LinkPost, Long> {

  @Query(value = "SELECT * FROM link_post p WHERE p.post_id = :postId", nativeQuery = true)
  Optional<LinkPost> findByPostId(Long postId);

  @Query(value = "DELETE FROM link_post p WHERE p.post_id = :postId", nativeQuery = true)
  void deleteByPostId(Long postId);
}
