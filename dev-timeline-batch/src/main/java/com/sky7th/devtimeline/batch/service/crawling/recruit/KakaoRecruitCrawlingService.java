package com.sky7th.devtimeline.batch.service.crawling.recruit;

import com.sky7th.devtimeline.batch.dto.CrawlingDto;
import com.sky7th.devtimeline.batch.service.crawling.CompanyCrawlingService;
import com.sky7th.devtimeline.batch.utils.CrawlingUtils;
import com.sky7th.devtimeline.batch.dto.CompanyDto;
import com.sky7th.devtimeline.crawlpost.company.domain.CompanyType;
import com.sky7th.devtimeline.crawlpost.company.domain.CompanyUrlType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoRecruitCrawlingService implements CompanyCrawlingService {

    private final WebDriver driver;

    private CompanyDto companyDto;

    @Override
    public List<CrawlingDto> crawling(CompanyDto companyDto) {
        this.companyDto = companyDto;
        int pageSize = getKakaoRecruitLastPageNum();

        return getAllCrawlingDtoUntilLastPage(pageSize);
    }

    private int getKakaoRecruitLastPageNum() {
        this.driver.get(companyDto.getCompanyUrl().getUrl());

        return Integer.parseInt(this.driver.findElement(By.className("btn_lst"))
                .getAttribute("href").split("=")[1]);
    }

   private List<CrawlingDto> getAllCrawlingDtoUntilLastPage(int pageSize) {
        List<CrawlingDto> crawlingItems = new ArrayList<>();

        for (int i = 1; i <= pageSize; i++) {
            this.driver.get(this.companyDto.getCompanyUrl().getUrl() + "?page=" + i);
            By by = By.className("list_jobs");
            WebElement element = CrawlingUtils.getWebElement(this.driver, by);
            crawlingItems.addAll(parseWebElement(element));
        }
        return crawlingItems;
    }

    @Override
    public List<CrawlingDto> parseWebElement(WebElement element) {
        if (element == null) {
            return new ArrayList<>();
        }
        return element.findElements(By.tagName("li")).stream()
                .map(this::getCrawlingDto)
                .collect(Collectors.toList());
    }

    @Override
    public CrawlingDto getCrawlingDto(WebElement element) {
        String title = element.findElement(By.className("tit_jobs")).getText();
        List<WebElement> periodSpanElements = element.findElement(By.className("list_info")).findElements(By.tagName("dd"));
        WebElement closingDateElement = periodSpanElements.stream()
                .filter(webElement -> webElement.getText().contains("년") || webElement.getText().contains("영입종료시"))
                .findFirst().orElse(null);
        String closingDate = closingDateElement==null ? "" : closingDateElement.getText();
        String contentUrl = element.findElement(By.tagName("a")).getAttribute("href");
        Pattern p = Pattern.compile("(?<=P-).+(?=\\?)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(contentUrl);
        String key = m.find() ? m.group() : "";

        return CrawlingDto.builder()
                .crawlId(CompanyType.KAKAO.toString()+"-"+ CompanyUrlType.RECRUIT.toString()+"-"+key)
                .companyUrl(this.companyDto.getCompanyUrl())
                .title(title)
                .closingDate(closingDate)
                .contentUrl(contentUrl)
                .build();
    }

}
