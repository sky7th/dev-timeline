package com.sky7th.devtimeline.core.domain.linkpost.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkPostRepository extends JpaRepository<LinkPost, Long> {

  Optional<LinkPost> findByPost_Id(Long postId);
}
