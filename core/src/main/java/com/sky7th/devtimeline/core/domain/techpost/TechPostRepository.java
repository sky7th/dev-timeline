package com.sky7th.devtimeline.core.domain.techpost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechPostRepository extends JpaRepository<TechPost, Long>, TechPostRepositoryCustom {
}
