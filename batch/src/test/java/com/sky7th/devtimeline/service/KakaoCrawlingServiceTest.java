package com.sky7th.devtimeline.service;

import com.sky7th.devtimeline.batch.config.SeleniumConfig;
import com.sky7th.devtimeline.batch.dto.CrawlingDto;
import com.sky7th.devtimeline.batch.service.crawling.KakaoCrawlingService;
import com.sky7th.devtimeline.core.domain.company.CompanyType;
import com.sky7th.devtimeline.core.domain.dto.CompanyDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {SeleniumConfig.class, KakaoCrawlingService.class})
class KakaoCrawlingServiceTest {

    @Autowired
    KakaoCrawlingService kakaoCrawlingService;

    @Test
    void testKakaoRecruitListCrawling() {
        // given
        CompanyDto kakaoCompany = CompanyDto.builder()
                .companyType(CompanyType.KAKAO)
                .url("https://careers.kakao.com/jobs")
                .build();

        // when
        List<CrawlingDto> crawlings = kakaoCrawlingService.crawling(kakaoCompany);

        // then
        assertTrue(crawlings.size() > 0);
    }

}
