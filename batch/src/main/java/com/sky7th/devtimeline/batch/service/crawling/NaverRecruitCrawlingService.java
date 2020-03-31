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
public class NaverRecruitCrawlingService implements CrawlingInterface {

    private final WebDriver driver;

    private CompanyDto companyDto;

    @Override
    public List<CrawlingDto> crawling(CompanyDto companyDto) {
        this.companyDto = companyDto;
        driver.get(this.companyDto.getCompanyUrl().getUrl());

        CrawlingUtils.clickMoreBtnUntilTheEnd(driver, By.className("more_btn"));

        By by = By.cssSelector("#jobListDiv > ul");
        WebElement element = CrawlingUtils.getWebElements(driver, by);

        return parseWebElement(element);
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
        String title = element.findElement(By.className("crd_tit")).getText();
        String endDate = element.findElement(By.className("crd_date")).getText();
        String url = element.findElement(By.tagName("a")).getAttribute("href");

        return CrawlingDto.builder()
                .companyUrl(this.companyDto.getCompanyUrl())
                .title(title)
                .closingDate(endDate)
                .contentUrl("https://recruit.navercorp.com" + url)
                .build();
    }
}