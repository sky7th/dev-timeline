package com.sky7th.devtimeline.web.repository.recruitPost;

import com.sky7th.devtimeline.core.domain.recruitpost.RecruitPost;

import java.util.List;

public interface RecruitPostWebRepositoryCustom {

    List<RecruitPost> findAllLimitDesc(long offset, long limit);
    List<RecruitPost> findByTitleContainingLimitDesc(String title, long offset, long limit);

}
