package com.sky7th.devtimeline.web.repository.techPost;

import com.sky7th.devtimeline.core.domain.post.techpost.TechPost;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;
import com.sky7th.devtimeline.web.service.dto.TechPostItem;

import java.util.List;

public interface TechPostWebRepositoryCustom {

    List<TechPostItem> findAllWithLikeCountAndIsLikeBySearchForm(PostSearchForm postSearchForm, Long userId);
    long countBySearchForm(PostSearchForm postSearchForm);


}
