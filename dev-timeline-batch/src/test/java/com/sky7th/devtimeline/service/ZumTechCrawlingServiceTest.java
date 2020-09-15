//package com.sky7th.devtimeline.service;
//
//import com.sky7th.devtimeline.batch.config.SeleniumConfig;
//import com.sky7th.devtimeline.batch.dto.CompanyDto;
//import com.sky7th.devtimeline.batch.dto.CrawlingDto;
//import com.sky7th.devtimeline.batch.service.crawling.WoowabrosTechCrawlingService;
//import com.sky7th.devtimeline.batch.service.crawling.ZumTechCrawlingService;
//import com.sky7th.devtimeline.core.domain.company.Company;
//import com.sky7th.devtimeline.core.domain.company.CompanyType;
//import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrl;
//import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrlType;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest(classes = {SeleniumConfig.class, ZumTechCrawlingService.class})
//class ZumTechCrawlingServiceTest {
//
//    @Autowired
//    ZumTechCrawlingService zumTechCrawlingService;
//
//    @Test
//    void testWooWCrawling() {
//        // given
//        CompanyUrl companyUrl = CompanyUrl.builder()
//                .companyUrlType(CompanyUrlType.TECH)
//                .url("https://zuminternet.github.io/")
//                .build();
//        companyUrl.setCompany(Company.builder()
//                .companyType(CompanyType.ZUM)
//                .build());
//
//        CompanyDto companyDto = CompanyDto.builder()
//                .companyUrl(companyUrl)
//                .build();
//
//        // when
//        List<CrawlingDto> crawlings = zumTechCrawlingService.crawling(companyDto);
//        crawlings.forEach(c -> {
//            System.out.println(c.getTitle() + " / " + c.getCrawlId() + " / " + c.getDate() + " / " + c.getContentUrl());
//        });
//        // then
//        assertTrue(crawlings.size() > 0);
//    }
//
//}