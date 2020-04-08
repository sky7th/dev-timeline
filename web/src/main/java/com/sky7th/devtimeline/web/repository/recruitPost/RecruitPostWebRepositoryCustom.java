package com.sky7th.devtimeline.web.repository.recruitPost;

import com.sky7th.devtimeline.core.domain.recruitpost.RecruitPost;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;

import java.util.List;

public interface RecruitPostWebRepositoryCustom {

    List<RecruitPost> findBySearchForm(PostSearchForm postSearchForm);
    long countBySearchForm(PostSearchForm postSearchForm);

}
