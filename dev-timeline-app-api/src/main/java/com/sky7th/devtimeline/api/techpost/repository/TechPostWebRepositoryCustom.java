package com.sky7th.devtimeline.api.techpost.repository;

import com.sky7th.devtimeline.crawlpost.CrawlPostSearchForm;
import com.sky7th.devtimeline.crawlpost.techpost.dto.TechPostItem;
import java.util.List;

public interface TechPostWebRepositoryCustom {

    List<TechPostItem> findAllWithLikeCountAndIsLikeBySearchForm(CrawlPostSearchForm postSearchForm);
    long countBySearchForm(CrawlPostSearchForm postSearchForm);


}
