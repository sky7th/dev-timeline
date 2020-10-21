package com.sky7th.devtimeline.api.techpost.service;

import com.sky7th.devtimeline.api.techpost.repository.TechPostWebRepository;
import com.sky7th.devtimeline.crawlpost.CrawlPostSearchForm;
import com.sky7th.devtimeline.crawlpost.techpost.dto.TechPostItem;
import com.sky7th.devtimeline.crawlpost.techpost.dto.TechPostViewResponseDtos;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TechPostService {

    private final TechPostWebRepository techPostWebRepository;

    @Transactional(readOnly = true)
    public TechPostViewResponseDtos findBySearchForm(CrawlPostSearchForm postSearchForm) {
        List<TechPostItem> techPosts = techPostWebRepository.findAllWithLikeCountAndIsLikeBySearchForm(postSearchForm);
        Long techPostCounts = null;
        if (postSearchForm.isFirstLoad()) {
            techPostCounts = techPostWebRepository.countBySearchForm(postSearchForm);
        }

        return TechPostViewResponseDtos.of(techPosts, techPostCounts);
    }

}
