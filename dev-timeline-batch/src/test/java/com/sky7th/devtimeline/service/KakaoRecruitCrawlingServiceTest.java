//package com.sky7th.devtimeline.service;
//
//import com.sky7th.devtimeline.batch.config.SeleniumConfig;
//import com.sky7th.devtimeline.batch.dto.CrawlingDto;
//import com.sky7th.devtimeline.batch.service.crawling.KakaoRecruitCrawlingService;
//import com.sky7th.devtimeline.core.domain.company.domain.Company;
//import com.sky7th.devtimeline.core.domain.company.domain.CompanyType;
//import com.sky7th.devtimeline.batch.dto.CompanyDto;
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
//@SpringBootTest(classes = {SeleniumConfig.class, KakaoRecruitCrawlingService.class})
//class KakaoRecruitCrawlingServiceTest {
//
//    @Autowired
//    KakaoRecruitCrawlingService kakaoRecruitCrawlingService;
//
//    @Test
//    void testKakaoRecruitListCrawling() {
//        // given
//        CompanyUrl companyUrl = CompanyUrl.builder()
//                .companyUrlType(CompanyUrlType.RECRUIT)
//                .url("https://careers.kakao.com/jobs")
//                .build();
//        companyUrl.setCompany(Company.builder()
//                .companyType(CompanyType.KAKAO)
//                .build());
//
//        CompanyDto companyDto = CompanyDto.builder()
//                .companyUrl(companyUrl)
//                .build();
//
//        // when
//        List<CrawlingDto> crawlings = kakaoRecruitCrawlingService.crawling(companyDto);
//        crawlings.forEach(c -> {
//            System.out.println(c.getTitle() + " / " + c.getCrawlId());
//        });
//        // then
//        assertTrue(crawlings.size() > 0);
//    }
//
//}
