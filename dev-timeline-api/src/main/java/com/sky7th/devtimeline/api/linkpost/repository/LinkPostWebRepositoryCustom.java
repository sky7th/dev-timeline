package com.sky7th.devtimeline.api.linkpost.repository;

import com.sky7th.devtimeline.core.domain.linkpost.dto.LinkPostItem;
import com.sky7th.devtimeline.core.domain.post.dto.PostSearchForm;
import java.util.List;
import java.util.Optional;

public interface LinkPostWebRepositoryCustom {

    List<LinkPostItem> findAllWithLikeCountAndIsLikeBySearchForm(PostSearchForm postSearchForm);
    Optional<LinkPostItem> findWithLikeCountAndIsLikeByIdAndUserId(Long id);
    long countBySearchForm(PostSearchForm postSearchForm);

}
