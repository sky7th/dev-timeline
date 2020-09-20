//package com.sky7th.devtimeline.service;
//
//import com.sky7th.devtimeline.batch.config.SeleniumConfig;
//import com.sky7th.devtimeline.batch.dto.CompanyDto;
//import com.sky7th.devtimeline.batch.dto.CrawlingDto;
//import com.sky7th.devtimeline.batch.service.crawling.NaverTechCrawlingService;
//import com.sky7th.devtimeline.core.domain.company.domain.Company;
//import com.sky7th.devtimeline.core.domain.company.domain.CompanyType;
//import com.sky7th.devtimeline.core.domain.company.domain.CompanyUrl;
//import com.sky7th.devtimeline.core.domain.company.domain.CompanyUrlType;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest(classes = {SeleniumConfig.class, NaverTechCrawlingService.class})
//class NaverTechCrawlingServiceTest {
//
//    @Autowired
//    NaverTechCrawlingService naverTechCrawlingService;
//
//    @Test
//    void testNaverRecruitListCrawling() {
//        // given
//        CompanyUrl companyUrl = CompanyUrl.builder()
//                .companyUrlType(CompanyUrlType.TECH)
//                .url("https://d2.naver.com/helloworld")
//                .build();
//        companyUrl.setCompany(Company.builder()
//                .companyType(CompanyType.NAVER)
//                .build());
//
//        CompanyDto companyDto = CompanyDto.builder()
//                .companyUrl(companyUrl)
//                .build();
//
//        // when
//        List<CrawlingDto> crawlings = naverTechCrawlingService.crawling(companyDto);
//        crawlings.forEach(c -> {
//            System.out.println(c.getTitle() + " / " + c.getThumbnailUrl());
//        });
//        // then
//        assertTrue(crawlings.size() > 0);
//    }
//
//}