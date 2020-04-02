package com.sky7th.devtimeline.batch.service;

import com.sky7th.devtimeline.batch.dto.CrawlingDto;
import com.sky7th.devtimeline.batch.service.crawling.CompanyCrawlingService;
import com.sky7th.devtimeline.batch.service.crawling.KakaoRecruitCrawlingService;
import com.sky7th.devtimeline.batch.service.crawling.NaverRecruitCrawlingService;
import com.sky7th.devtimeline.batch.service.crawling.NaverTechCrawlingService;
import com.sky7th.devtimeline.core.domain.company.CompanyType;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrl;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrlRepository;
import com.sky7th.devtimeline.batch.dto.CompanyDto;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrlType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CrawlingService {

    private final CompanyUrlRepository companyUrlRepository;
    private final NaverRecruitCrawlingService naverRecruitCrawlingService;
    private final NaverTechCrawlingService naverTechCrawlingService;
    private final KakaoRecruitCrawlingService kakaoRecruitCrawlingService;

    @Transactional(readOnly = true)
    public List<CrawlingDto> crawlingAllCompany() {
        List<CrawlingDto> crawlingDtos = new ArrayList<>();

        List<CompanyUrl> companyUrls = companyUrlRepository.findAll();
        companyUrls.forEach(companyUrl -> {
            CompanyDto companyDto = CompanyDto.builder()
                    .companyUrl(companyUrl)
                    .build();

            crawlingDtos.addAll(crawling(companyDto));
        });

        return crawlingDtos;
    }

    public List<CrawlingDto> crawling(CompanyDto companyDto) {
        Map<String, CompanyCrawlingService> crawlingServiceMap = new HashMap<>();
        crawlingServiceMap.put(CompanyType.NAVER.getName()+"/"+CompanyUrlType.RECRUIT.getName(), naverRecruitCrawlingService);
        crawlingServiceMap.put(CompanyType.NAVER.getName()+"/"+CompanyUrlType.TECH.getName(), naverTechCrawlingService);
        crawlingServiceMap.put(CompanyType.KAKAO.getName()+"/"+CompanyUrlType.RECRUIT.getName(), kakaoRecruitCrawlingService);
        String key = companyDto.getCompanyUrl().getCompany().getCompanyType().getName()
                + "/" + companyDto.getCompanyUrl().getCompanyUrlType().getName();

        List<CrawlingDto> crawling = crawlingServiceMap.get(key).crawling(companyDto);
        log.info("{} 에서 {} 개의 새 데이터를 가져옴",key, crawling.size());

        return crawling;
    }

}
