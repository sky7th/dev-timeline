package com.sky7th.devtimeline.web.repository.LinkPost;

import com.sky7th.devtimeline.core.domain.post.linkpost.LinkPost;
import com.sky7th.devtimeline.web.service.dto.LinkPostDto;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;

import java.util.List;
import java.util.Optional;

public interface LinkPostWebRepositoryCustom {

    List<LinkPostDto> findAllWithLikeCountAndIsLikeBySearchForm(PostSearchForm postSearchForm, Long userId);
    Optional<LinkPostDto> findWithLikeCountAndIsLikeByIdAndUserId(Long id, Long userId);
    long countBySearchForm(PostSearchForm postSearchForm);

}
