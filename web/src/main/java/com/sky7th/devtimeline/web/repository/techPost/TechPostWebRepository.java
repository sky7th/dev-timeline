package com.sky7th.devtimeline.web.repository.techPost;

import com.sky7th.devtimeline.core.domain.techpost.TechPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechPostWebRepository extends JpaRepository<TechPost, Long>, TechPostWebRepositoryCustom {
}
