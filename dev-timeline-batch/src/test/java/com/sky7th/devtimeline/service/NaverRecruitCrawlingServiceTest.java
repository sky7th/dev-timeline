//package com.sky7th.devtimeline.service;
//
//import com.sky7th.devtimeline.batch.config.SeleniumConfig;
//import com.sky7th.devtimeline.batch.dto.CrawlingDto;
//import com.sky7th.devtimeline.batch.service.crawling.NaverRecruitCrawlingService;
//import com.sky7th.devtimeline.core.domain.company.Company;
//import com.sky7th.devtimeline.core.domain.company.CompanyType;
//import com.sky7th.devtimeline.batch.dto.CompanyDto;
//import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrl;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest(classes = {SeleniumConfig.class, NaverRecruitCrawlingService.class})
//class NaverRecruitCrawlingServiceTest {
//
//    @Autowired
//    NaverRecruitCrawlingService naverRecruitCrawlingService;
//
//    @Test
//    void testNaverRecruitListCrawling() {
//        // given
//        CompanyUrl companyUrl = CompanyUrl.builder()
//                .url("https://recruit.navercorp.com/naver/job/list/developer")
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
//        List<CrawlingDto> crawlings = naverRecruitCrawlingService.crawling(companyDto);
//        crawlings.forEach(c -> {
//            System.out.println(c.getTitle() + " / " + c.getCrawlId());
//        });
//        // then
//        assertTrue(crawlings.size() > 0);
//    }
//
//}