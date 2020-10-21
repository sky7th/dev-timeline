package com.sky7th.devtimeline.generalpost.linkpost.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkPostRepository extends JpaRepository<LinkPost, Long> {

  Optional<LinkPost> findByPost_Id(Long postId);
}
