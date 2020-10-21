package com.sky7th.devtimeline.api.recruitpost.service;

import com.sky7th.devtimeline.api.recruitpost.repository.RecruitPostWebRepository;
import com.sky7th.devtimeline.crawlpost.CrawlPostSearchForm;
import com.sky7th.devtimeline.crawlpost.recruitpost.dto.RecruitPostItem;
import com.sky7th.devtimeline.crawlpost.recruitpost.dto.RecruitPostViewResponseDtos;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RecruitPostService {

    private final RecruitPostWebRepository recruitPostWebRepository;

    @Transactional(readOnly = true)
    public RecruitPostViewResponseDtos findBySearchForm(CrawlPostSearchForm postSearchForm) {
        List<RecruitPostItem> recruitPosts = recruitPostWebRepository.findAllWithLikeCountAndIsLikeBySearchForm(postSearchForm);
        Long recruitPostCounts = null;
        if (postSearchForm.isFirstLoad()) {
            recruitPostCounts = recruitPostWebRepository.countBySearchForm(postSearchForm);
        }

        return RecruitPostViewResponseDtos.of(recruitPosts, recruitPostCounts);
    }

}
