//package com.sky7th.devtimeline.service.tech;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import com.sky7th.devtimeline.batch.config.SeleniumConfig;
//import com.sky7th.devtimeline.batch.dto.CompanyDto;
//import com.sky7th.devtimeline.batch.dto.CrawlingDto;
//import com.sky7th.devtimeline.batch.service.crawling.tech.LineTechCrawlingService;
//import com.sky7th.devtimeline.crawlpost.company.domain.Company;
//import com.sky7th.devtimeline.crawlpost.company.domain.CompanyType;
//import com.sky7th.devtimeline.crawlpost.company.domain.CompanyUrl;
//import com.sky7th.devtimeline.crawlpost.company.domain.CompanyUrlType;
//import java.util.List;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest(classes = {SeleniumConfig.class, LineTechCrawlingService.class})
//class LineTechCrawlingServiceTest {
//
//    @Autowired
//    LineTechCrawlingService lineTechCrawlingService;
//
//    @Test
//    void testLineTechCrawling() {
//        // given
//        CompanyUrl companyUrl = CompanyUrl.builder()
//                .companyUrlType(CompanyUrlType.TECH)
//                .url("https://engineering.linecorp.com/ko/blog/")
//                .build();
//        companyUrl.setCompany(Company.builder()
//                .companyType(CompanyType.LINE)
//                .build());
//
//        CompanyDto companyDto = CompanyDto.builder()
//                .companyUrl(companyUrl)
//                .build();
//
//        // when
//        List<CrawlingDto> crawlings = lineTechCrawlingService.crawling(companyDto);
////        crawlings.forEach(c -> {
////            System.out.println(c.getTitle() + " / " + c.getCrawlId());
////        });
//        // then
//        assertTrue(crawlings.size() > 0);
//    }
//
//}