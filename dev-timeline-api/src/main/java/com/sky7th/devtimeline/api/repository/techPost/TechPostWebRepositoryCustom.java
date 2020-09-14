package com.sky7th.devtimeline.api.repository.techPost;

import com.sky7th.devtimeline.api.security.UserPrincipal;
import com.sky7th.devtimeline.api.service.dto.PostSearchForm;
import com.sky7th.devtimeline.api.service.dto.TechPostItem;

import java.util.List;

public interface TechPostWebRepositoryCustom {

    List<TechPostItem> findAllWithLikeCountAndIsLikeBySearchForm(PostSearchForm postSearchForm, UserPrincipal userPrincipal);
    long countBySearchForm(PostSearchForm postSearchForm, UserPrincipal userPrincipal);


}
