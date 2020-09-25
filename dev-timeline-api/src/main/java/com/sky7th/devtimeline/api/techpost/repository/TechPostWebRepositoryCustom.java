package com.sky7th.devtimeline.api.techpost.repository;

import com.sky7th.devtimeline.core.domain.post.dto.PostSearchForm;
import com.sky7th.devtimeline.core.domain.techpost.dto.TechPostItem;
import java.util.List;

public interface TechPostWebRepositoryCustom {

    List<TechPostItem> findAllWithLikeCountAndIsLikeBySearchForm(PostSearchForm postSearchForm);
    long countBySearchForm(PostSearchForm postSearchForm);


}
