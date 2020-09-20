package com.sky7th.devtimeline.api.recruitpost.service;

import com.sky7th.devtimeline.api.recruitpost.repository.RecruitPostWebRepository;
import com.sky7th.devtimeline.core.domain.post.dto.PostSearchForm;
import com.sky7th.devtimeline.core.domain.recruitpost.dto.RecruitPostItem;
import com.sky7th.devtimeline.core.domain.recruitpost.dto.RecruitPostViewResponseDtos;
import com.sky7th.devtimeline.core.domain.user.dto.UserContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RecruitPostService {

    private final RecruitPostWebRepository recruitPostWebRepository;

    @Transactional(readOnly = true)
    public RecruitPostViewResponseDtos findBySearchForm(PostSearchForm postSearchForm, UserContext userContext) {
        List<RecruitPostItem> recruitPosts = recruitPostWebRepository.findAllWithLikeCountAndIsLikeBySearchForm(postSearchForm, userContext);
        long recruitPostCounts = recruitPostWebRepository.countBySearchForm(postSearchForm, userContext);

        return RecruitPostViewResponseDtos.of(recruitPosts, recruitPostCounts);
    }

}
