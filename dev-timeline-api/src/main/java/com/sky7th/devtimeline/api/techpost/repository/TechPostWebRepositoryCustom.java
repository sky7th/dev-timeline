package com.sky7th.devtimeline.api.techpost.repository;

import com.sky7th.devtimeline.api.security.UserPrincipal;
import com.sky7th.devtimeline.core.domain.post.dto.PostSearchForm;
import com.sky7th.devtimeline.core.domain.techpost.dto.TechPostItem;
import com.sky7th.devtimeline.core.domain.user.dto.UserContext;
import java.util.List;

public interface TechPostWebRepositoryCustom {

    List<TechPostItem> findAllWithLikeCountAndIsLikeBySearchForm(PostSearchForm postSearchForm, UserContext userContext);
    long countBySearchForm(PostSearchForm postSearchForm, UserContext userContext);


}
