package com.sky7th.devtimeline.api.service;

import com.sky7th.devtimeline.api.repository.recruitPost.RecruitPostWebRepository;
import com.sky7th.devtimeline.api.security.UserPrincipal;
import com.sky7th.devtimeline.api.service.dto.PostSearchForm;
import com.sky7th.devtimeline.api.service.dto.RecruitPostItem;
import com.sky7th.devtimeline.api.service.dto.RecruitPostView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecruitPostService {

    private final RecruitPostWebRepository recruitPostWebRepository;

    @Transactional(readOnly = true)
    public RecruitPostView findBySearchForm(PostSearchForm postSearchForm, UserPrincipal userPrincipal) {
        List<RecruitPostItem> recruitPosts = recruitPostWebRepository.findAllWithLikeCountAndIsLikeBySearchForm(postSearchForm, userPrincipal);
        long recruitPostCounts = recruitPostWebRepository.countBySearchForm(postSearchForm, userPrincipal);

        return new RecruitPostView(recruitPosts, recruitPostCounts);
    }

}
