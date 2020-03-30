package com.sky7th.devtimeline.batch.service;

import com.sky7th.devtimeline.batch.dto.CrawlingDto;
import com.sky7th.devtimeline.batch.service.crawling.CrawlingInterface;
import com.sky7th.devtimeline.batch.service.crawling.KakaoCrawlingService;
import com.sky7th.devtimeline.batch.service.crawling.NaverCrawlingService;
import com.sky7th.devtimeline.core.domain.company.CompanyType;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrl;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrlRepository;
import com.sky7th.devtimeline.core.domain.dto.CompanyDto;
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
    private final NaverCrawlingService naverCrawlingService;
    private final KakaoCrawlingService kakaoCrawlingService;

    @Transactional(readOnly = true)
    public List<CrawlingDto> crawlingAllCompany() {
        List<CrawlingDto> crawlingDtos = new ArrayList<>();

        List<CompanyUrl> companyUrls = companyUrlRepository.findAll();
        companyUrls.forEach(companyUrl -> {
            CompanyDto companyDto = CompanyDto.builder()
                    .companyType(companyUrl.getCompany().getCompanyType())
                    .companyUrlType(companyUrl.getCompanyUrlType())
                    .url(companyUrl.getUrl())
                    .build();

            crawlingDtos.addAll(crawling(companyDto));
        });

        return crawlingDtos;
    }

    private List<CrawlingDto> crawling(CompanyDto companyDto) {
        Map<CompanyType, CrawlingInterface> crawlingServiceMap = new HashMap<>();
        crawlingServiceMap.put(CompanyType.NAVER, naverCrawlingService);
        crawlingServiceMap.put(CompanyType.KAKAO, kakaoCrawlingService);

        List<CrawlingDto> crawling = crawlingServiceMap.get(companyDto.getCompanyType()).crawling(companyDto);
        log.info("{} 에서 {} 개의 새 데이터를 가져옴", companyDto.getCompanyType(), crawling.size());

        return crawling;
    }

}
