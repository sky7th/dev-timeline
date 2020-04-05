package com.sky7th.devtimeline.web.repository.techPost;

import com.sky7th.devtimeline.core.domain.techpost.TechPost;

import java.util.List;

public interface TechPostWebRepositoryCustom {

    List<TechPost> findAllLimitDesc(long offset, long limit);
    List<TechPost> findByTitleContainingLimitDesc(String title, long offset, long limit);

}
