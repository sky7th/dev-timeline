package com.sky7th.devtimeline.batch.service.crawling;

import com.sky7th.devtimeline.batch.dto.CrawlingDto;
import com.sky7th.devtimeline.batch.utils.CrawlingUtils;
import com.sky7th.devtimeline.batch.dto.CompanyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
            By by = By.className("list_notice");
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
        String title = element.findElement(By.className("txt_tit")).getText();
        List<WebElement> periodSpanElements = element.findElement(By.className("txt_period")).findElements(By.tagName("span"));
        WebElement closingDateElement = periodSpanElements.stream()
                .filter(webElement -> webElement.getText().contains("년") || webElement.getText().contains("영입종료시"))
                .findFirst().orElse(null);
        String closingDate = closingDateElement==null ? "" : closingDateElement.getText();
        String contentUrl = element.findElement(By.tagName("a")).getAttribute("href");

        return CrawlingDto.builder()
                .companyUrl(this.companyDto.getCompanyUrl())
                .title(title)
                .closingDate(closingDate)
                .contentUrl(contentUrl)
                .build();
    }

}
