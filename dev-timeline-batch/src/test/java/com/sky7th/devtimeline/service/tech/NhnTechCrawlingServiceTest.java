//package com.sky7th.devtimeline.service.tech;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import com.sky7th.devtimeline.batch.config.SeleniumConfig;
//import com.sky7th.devtimeline.batch.dto.CompanyDto;
//import com.sky7th.devtimeline.batch.dto.CrawlingDto;
//import com.sky7th.devtimeline.batch.service.crawling.tech.NhnTechCrawlingService;
//import com.sky7th.devtimeline.crawlpost.company.domain.Company;
//import com.sky7th.devtimeline.crawlpost.company.domain.CompanyType;
//import com.sky7th.devtimeline.crawlpost.company.domain.CompanyUrl;
//import com.sky7th.devtimeline.crawlpost.company.domain.CompanyUrlType;
//import java.util.List;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest(classes = {SeleniumConfig.class, NhnTechCrawlingService.class})
//class NhnTechCrawlingServiceTest {
//
//    @Autowired
//    NhnTechCrawlingService nhnTechCrawlingService;
//
//    @Test
//    void testWooWCrawling() {
//        // given
//        CompanyUrl companyUrl = CompanyUrl.builder()
//                .companyUrlType(CompanyUrlType.TECH)
//                .url("https://meetup.toast.com/")
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
//        List<CrawlingDto> crawlings = nhnTechCrawlingService.crawling(companyDto);
////        Long i = 1L;
////        crawlings.forEach(c -> {
////            System.out.println(i + " / " + c.getTitle() + " / " + c.getCrawlId() + " / " + c.getDate() + " / " + c.getContentUrl());
////        });
//        // then
//        assertTrue(crawlings.size() > 0);
//    }
//
//}