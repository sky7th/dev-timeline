package com.sky7th.devtimeline.api.recruitpost.repository;

import com.sky7th.devtimeline.core.domain.post.dto.PostSearchForm;
import com.sky7th.devtimeline.core.domain.recruitpost.dto.RecruitPostItem;
import java.util.List;

public interface RecruitPostWebRepositoryCustom {

    List<RecruitPostItem> findAllWithLikeCountAndIsLikeBySearchForm(PostSearchForm postSearchForm);
    long countBySearchForm(PostSearchForm postSearchForm);

}
