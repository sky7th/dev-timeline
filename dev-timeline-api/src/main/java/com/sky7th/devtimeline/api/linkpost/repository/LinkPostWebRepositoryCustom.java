package com.sky7th.devtimeline.api.linkpost.repository;

import com.sky7th.devtimeline.core.domain.linkpost.dto.LinkPostItem;
import com.sky7th.devtimeline.core.domain.post.dto.PostSearchForm;
import com.sky7th.devtimeline.core.domain.user.dto.UserContext;
import java.util.List;
import java.util.Optional;

public interface LinkPostWebRepositoryCustom {

    List<LinkPostItem> findAllWithLikeCountAndIsLikeBySearchForm(PostSearchForm postSearchForm, UserContext userContext);
    Optional<LinkPostItem> findWithLikeCountAndIsLikeByIdAndUserId(Long id, UserContext userContext);
    long countBySearchForm(PostSearchForm postSearchForm, UserContext userContext);

}
