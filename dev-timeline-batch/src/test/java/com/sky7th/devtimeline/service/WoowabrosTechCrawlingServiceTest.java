//package com.sky7th.devtimeline.service;
//
//import com.sky7th.devtimeline.batch.config.SeleniumConfig;
//import com.sky7th.devtimeline.batch.dto.CompanyDto;
//import com.sky7th.devtimeline.batch.dto.CrawlingDto;
//import com.sky7th.devtimeline.batch.service.crawling.WoowabrosTechCrawlingService;
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
//@SpringBootTest(classes = {SeleniumConfig.class, WoowabrosTechCrawlingService.class})
//class WoowabrosTechCrawlingServiceTest {
//
//    @Autowired
//    WoowabrosTechCrawlingService woowabrosTechCrawlingService;
//
//    @Test
//    void testWooWCrawling() {
//        // given
//        CompanyUrl companyUrl = CompanyUrl.builder()
//                .companyUrlType(CompanyUrlType.TECH)
//                .url("https://woowabros.github.io/")
//                .build();
//        companyUrl.setCompany(Company.builder()
//                .companyType(CompanyType.WOOWABROS)
//                .build());
//
//        CompanyDto companyDto = CompanyDto.builder()
//                .companyUrl(companyUrl)
//                .build();
//
//        // when
//        List<CrawlingDto> crawlings = woowabrosTechCrawlingService.crawling(companyDto);
//        crawlings.forEach(c -> {
//            System.out.println(c.getTitle() + " / " + c.getCrawlId());
//        });
//        // then
//        assertTrue(crawlings.size() > 0);
//    }
//
//}