package com.sky7th.devtimeline.web.repository.LinkPost;

import com.sky7th.devtimeline.web.service.dto.LinkPostItem;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;

import java.util.List;
import java.util.Optional;

public interface LinkPostWebRepositoryCustom {

    List<LinkPostItem> findAllWithLikeCountAndIsLikeBySearchForm(PostSearchForm postSearchForm, Long userId);
    Optional<LinkPostItem> findWithLikeCountAndIsLikeByIdAndUserId(Long id, Long userId);
    long countBySearchForm(PostSearchForm postSearchForm);

}
