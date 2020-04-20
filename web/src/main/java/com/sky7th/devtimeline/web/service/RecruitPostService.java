package com.sky7th.devtimeline.web.service;

import com.sky7th.devtimeline.core.domain.post.recruitpost.RecruitPost;
import com.sky7th.devtimeline.web.repository.recruitPost.RecruitPostWebRepository;
import com.sky7th.devtimeline.web.service.dto.PostSearchForm;
import com.sky7th.devtimeline.web.service.dto.RecruitPostViewItems;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecruitPostService {

    private final RecruitPostWebRepository recruitPostWebRepository;

    @Transactional(readOnly = true)
    public RecruitPostViewItems findBySearchForm(PostSearchForm postSearchForm) {
        List<RecruitPost> recruitPosts = recruitPostWebRepository.findBySearchForm(postSearchForm);
        long recruitPostCounts = recruitPostWebRepository.countBySearchForm(postSearchForm);

        return new RecruitPostViewItems(recruitPosts, recruitPostCounts);
    }

}
