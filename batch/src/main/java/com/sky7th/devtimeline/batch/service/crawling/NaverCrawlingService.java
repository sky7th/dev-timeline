package com.sky7th.devtimeline.batch.service.crawling;

import com.sky7th.devtimeline.batch.domain.company.Company;
import com.sky7th.devtimeline.batch.dto.CrawlingDto;
import com.sky7th.devtimeline.batch.utils.CrawlingUtils;
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
public class NaverCrawlingService {

    private final WebDriver driver;

    public List<CrawlingDto> crawling(Company company) {
        driver.get(company.getUrl());

        CrawlingUtils.clickMoreBtnUntilTheEnd(driver, By.className("more_btn"));

        By by = By.cssSelector("#jobListDiv > ul");
        WebElement element = CrawlingUtils.getWebElements(driver, company.getUrl(), by);

        return parseWebElement(company, element);
    }

    private List<CrawlingDto> parseWebElement(Company company, WebElement element) {
        if (element == null) {
            return new ArrayList<>();
        }
        return element.findElements(By.tagName("li")).stream()
                .map(li -> getCrawlingDto(company, li))
                .collect(Collectors.toList());
    }

    private CrawlingDto getCrawlingDto(Company company, WebElement li) {
        String title = li.findElement(By.className("crd_tit")).getText();
        String endDate = li.findElement(By.className("crd_date")).getText();
        String url = li.findElement(By.tagName("a")).getAttribute("href");

        return CrawlingDto.builder()
                .name(company.getName())
                .title(title)
                .date(endDate)
                .url("https://recruit.navercorp.com" + url)
                .build();
    }
}