package com.sky7th.devtimeline.service;

import com.sky7th.devtimeline.batch.config.SeleniumConfig;
import com.sky7th.devtimeline.batch.domain.company.Company;
import com.sky7th.devtimeline.batch.dto.CrawlingDto;
import com.sky7th.devtimeline.batch.service.crawling.NaverCrawlingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {SeleniumConfig.class, NaverCrawlingService.class})
class NaverCrawlingServiceTest {

    @Autowired
    NaverCrawlingService naverCrawlingService;

    @Test
    void testNaverRecruitListCrawling() {
        // given
        Company naverCorp = Company.builder()
                .name("네이버")
                .url("https://recruit.navercorp.com/naver/job/list/developer")
                .build();

        // when
        List<CrawlingDto> crawlings = naverCrawlingService.crawling(naverCorp);

        // then
        assertTrue(crawlings.size() > 0);
    }

}