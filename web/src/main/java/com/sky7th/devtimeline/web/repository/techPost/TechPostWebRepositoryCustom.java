package com.sky7th.devtimeline.web.repository.techPost;

import com.sky7th.devtimeline.core.domain.post.techpost.TechPost;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;

import java.util.List;

public interface TechPostWebRepositoryCustom {

    List<TechPost> findBySearchForm(PostSearchForm postSearchForm);
    long countBySearchForm(PostSearchForm postSearchForm);


}
