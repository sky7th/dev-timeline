package com.sky7th.devtimeline.api.recruitpost.repository;

import com.sky7th.devtimeline.core.domain.post.dto.PostSearchForm;
import com.sky7th.devtimeline.core.domain.recruitpost.dto.RecruitPostItem;
import com.sky7th.devtimeline.core.domain.user.dto.UserContext;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;

public interface RecruitPostWebRepositoryCustom {

    List<RecruitPostItem> findAllWithLikeCountAndIsLikeBySearchForm(PostSearchForm postSearchForm, UserContext userContext);
    long countBySearchForm(PostSearchForm postSearchForm, UserContext userContext);

}
