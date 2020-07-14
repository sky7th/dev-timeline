package com.sky7th.devtimeline.web.service;

import com.sky7th.devtimeline.core.domain.post.recruitpost.RecruitPost;
import com.sky7th.devtimeline.web.repository.recruitPost.RecruitPostWebRepository;
import com.sky7th.devtimeline.web.security.UserPrincipal;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;
import com.sky7th.devtimeline.web.service.dto.RecruitPostItem;
import com.sky7th.devtimeline.web.service.dto.RecruitPostView;
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
