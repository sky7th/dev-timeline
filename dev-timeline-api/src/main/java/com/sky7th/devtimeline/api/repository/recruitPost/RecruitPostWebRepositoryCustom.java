package com.sky7th.devtimeline.api.repository.recruitPost;

import com.sky7th.devtimeline.api.security.UserPrincipal;
import com.sky7th.devtimeline.api.service.dto.PostSearchForm;
import com.sky7th.devtimeline.api.service.dto.RecruitPostItem;

import java.util.List;

public interface RecruitPostWebRepositoryCustom {

    List<RecruitPostItem> findAllWithLikeCountAndIsLikeBySearchForm(PostSearchForm postSearchForm, UserPrincipal userPrincipal);
    long countBySearchForm(PostSearchForm postSearchForm, UserPrincipal userPrincipal);

}
