package com.sky7th.devtimeline.api.recruitpost.repository;

import com.sky7th.devtimeline.crawlpost.CrawlPostSearchForm;
import com.sky7th.devtimeline.crawlpost.recruitpost.dto.RecruitPostItem;
import java.util.List;

public interface RecruitPostWebRepositoryCustom {

    List<RecruitPostItem> findAllWithLikeCountAndIsLikeBySearchForm(
        CrawlPostSearchForm postSearchForm);
    long countBySearchForm(CrawlPostSearchForm postSearchForm);

}
