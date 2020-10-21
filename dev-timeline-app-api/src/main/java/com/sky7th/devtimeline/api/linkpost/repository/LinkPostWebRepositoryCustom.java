package com.sky7th.devtimeline.api.linkpost.repository;

import com.sky7th.devtimeline.api.linkpost.dto.LinkPostItem;
import com.sky7th.devtimeline.generalpost.linkpost.dto.LinkPostSearchForm;
import java.util.List;
import java.util.Optional;

public interface LinkPostWebRepositoryCustom {

    List<LinkPostItem> findAllWithLikeCountAndIsLikeBySearchForm(LinkPostSearchForm postSearchForm);
    Optional<LinkPostItem> findWithLikeCountAndIsLikeByIdAndUserId(Long id);
    long countBySearchForm(LinkPostSearchForm postSearchForm);

}
