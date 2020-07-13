package com.sky7th.devtimeline.web.repository.recruitPost;

import com.sky7th.devtimeline.core.domain.post.recruitpost.RecruitPost;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;
import com.sky7th.devtimeline.web.service.dto.RecruitPostItem;

import java.util.List;

public interface RecruitPostWebRepositoryCustom {

    List<RecruitPostItem> findAllWithLikeCountAndIsLikeBySearchForm(PostSearchForm postSearchForm, Long userId);
    long countBySearchForm(PostSearchForm postSearchForm);

}
