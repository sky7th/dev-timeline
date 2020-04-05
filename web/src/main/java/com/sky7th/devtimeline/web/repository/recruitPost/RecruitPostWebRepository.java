package com.sky7th.devtimeline.web.repository.recruitPost;

import com.sky7th.devtimeline.core.domain.recruitpost.RecruitPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitPostWebRepository extends JpaRepository<RecruitPost, Long>, RecruitPostWebRepositoryCustom {
}
