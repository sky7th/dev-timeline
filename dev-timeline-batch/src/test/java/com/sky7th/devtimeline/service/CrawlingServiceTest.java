package com.sky7th.devtimeline.service;

import com.sky7th.devtimeline.batch.dto.CrawlingDto;
import com.sky7th.devtimeline.batch.service.CrawlingService;
import com.sky7th.devtimeline.core.domain.company.Company;
import com.sky7th.devtimeline.core.domain.company.CompanyRepository;
import com.sky7th.devtimeline.core.domain.company.CompanyType;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrl;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrlRepository;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrlType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CrawlingServiceTest {

    @Autowired
    CrawlingService crawlingService;

    @Autowired
    CompanyUrlRepository companyUrlRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Test
    void testCrawlingAllCompany() {
        // given
        Company kakaoCompany = Company.builder()
                .companyType(CompanyType.KAKAO)
                .build();

        Company naverCompany = Company.builder()
                .companyType(CompanyType.NAVER)
                .build();

        companyRepository.save(kakaoCompany);
        companyRepository.save(naverCompany);

        CompanyUrl kakaoCompanyUrl = CompanyUrl.builder()
                .companyUrlType(CompanyUrlType.RECRUIT)
                .url("https://careers.kakao.com/jobs")
                .build();
        kakaoCompanyUrl.setCompany(companyRepository.findByCompanyType(CompanyType.KAKAO));

        CompanyUrl naverCompanyUrl = CompanyUrl.builder()
                .companyUrlType(CompanyUrlType.RECRUIT)
                .url("https://recruit.navercorp.com/naver/job/list/developer")
                .build();
        naverCompanyUrl.setCompany(companyRepository.findByCompanyType(CompanyType.NAVER));

        companyUrlRepository.save(kakaoCompanyUrl);
        companyUrlRepository.save(naverCompanyUrl);

        // when
        List<CrawlingDto> crawlings = crawlingService.crawlingAllCompany();

        // then
        assertTrue(crawlings.size() > 0);
    }
}
