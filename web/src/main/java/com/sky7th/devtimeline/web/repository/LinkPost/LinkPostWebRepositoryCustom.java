package com.sky7th.devtimeline.web.repository.LinkPost;

import com.sky7th.devtimeline.web.security.UserPrincipal;
import com.sky7th.devtimeline.web.service.dto.LinkPostItem;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;

import java.util.List;
import java.util.Optional;

public interface LinkPostWebRepositoryCustom {

    List<LinkPostItem> findAllWithLikeCountAndIsLikeBySearchForm(PostSearchForm postSearchForm, UserPrincipal userPrincipal);
    Optional<LinkPostItem> findWithLikeCountAndIsLikeByIdAndUserId(Long id, UserPrincipal userPrincipal);
    long countBySearchForm(PostSearchForm postSearchForm, UserPrincipal userPrincipal);

}
