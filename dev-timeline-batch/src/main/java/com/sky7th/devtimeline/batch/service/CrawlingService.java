package com.sky7th.devtimeline.batch.service;

import com.sky7th.devtimeline.batch.dto.CrawlingDto;
import com.sky7th.devtimeline.batch.service.crawling.*;
import com.sky7th.devtimeline.batch.service.crawling.recruit.KakaoRecruitCrawlingService;
import com.sky7th.devtimeline.batch.service.crawling.recruit.NaverRecruitCrawlingService;
import com.sky7th.devtimeline.batch.service.crawling.tech.KakaoTechCrawlingService;
import com.sky7th.devtimeline.batch.service.crawling.tech.LineTechCrawlingService;
import com.sky7th.devtimeline.batch.service.crawling.tech.NaverTechCrawlingService;
import com.sky7th.devtimeline.batch.service.crawling.tech.NhnTechCrawlingService;
import com.sky7th.devtimeline.batch.service.crawling.tech.WoowabrosTechCrawlingService;
import com.sky7th.devtimeline.batch.service.crawling.tech.ZumTechCrawlingService;
import com.sky7th.devtimeline.crawlpost.company.domain.CompanyType;
import com.sky7th.devtimeline.crawlpost.company.domain.CompanyUrl;
import com.sky7th.devtimeline.crawlpost.company.domain.CompanyUrlRepository;
import com.sky7th.devtimeline.batch.dto.CompanyDto;
import com.sky7th.devtimeline.crawlpost.company.domain.CompanyUrlType;
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
    private final KakaoTechCrawlingService kakaoTechCrawlingService;
    private final WoowabrosTechCrawlingService woowabrosTechCrawlingService;
    private final LineTechCrawlingService lineTechCrawlingService;
    private final ZumTechCrawlingService zumTechCrawlingService;
    private final NhnTechCrawlingService nhnTechCrawlingService;

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
        crawlingServiceMap.put(CompanyType.KAKAO.getName()+"/"+CompanyUrlType.TECH.getName(), kakaoTechCrawlingService);
        crawlingServiceMap.put(CompanyType.WOOWABROS.getName()+"/"+CompanyUrlType.TECH.getName(), woowabrosTechCrawlingService);
        crawlingServiceMap.put(CompanyType.LINE.getName()+"/"+CompanyUrlType.TECH.getName(), lineTechCrawlingService);
        crawlingServiceMap.put(CompanyType.ZUM.getName()+"/"+CompanyUrlType.TECH.getName(), zumTechCrawlingService);
        crawlingServiceMap.put(CompanyType.NHN.getName()+"/"+CompanyUrlType.TECH.getName(), nhnTechCrawlingService);
        String key = companyDto.getCompanyUrl().getCompany().getCompanyType().getName()
                + "/" + companyDto.getCompanyUrl().getCompanyUrlType().getName();

        List<CrawlingDto> crawling = crawlingServiceMap.get(key).crawling(companyDto);
        log.info("{} 에서 {} 개의 새 데이터를 가져옴",key, crawling.size());

        return crawling;
    }

}
