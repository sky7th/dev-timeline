package com.sky7th.devtimeline.web.repository.LinkPost;

import com.sky7th.devtimeline.core.domain.linkpost.LinkPost;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;

import java.util.List;

public interface LinkPostWebRepositoryCustom {

    List<LinkPost> findBySearchForm(PostSearchForm postSearchForm);
    long countBySearchForm(PostSearchForm postSearchForm);

}
