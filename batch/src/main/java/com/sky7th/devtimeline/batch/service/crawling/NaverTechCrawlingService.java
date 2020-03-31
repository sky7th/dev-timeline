package com.sky7th.devtimeline.batch.service.crawling;

import com.sky7th.devtimeline.batch.dto.CompanyDto;
import com.sky7th.devtimeline.batch.dto.CrawlingDto;
import com.sky7th.devtimeline.batch.utils.CrawlingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class NaverTechCrawlingService implements CrawlingInterface {

    private final WebDriver driver;

    private CompanyDto companyDto;

    @Override
    public List<CrawlingDto> crawling(CompanyDto companyDto) {
        this.companyDto = companyDto;
        driver.get(companyDto.getCompanyUrl().getUrl());

        return getAllCrawlingDtoUntilLastPage();
    }

    private List<CrawlingDto> getAllCrawlingDtoUntilLastPage() {
        List<CrawlingDto> crawlingItems = new ArrayList<>();
        int pageNum = 0;

        while (true) {
            try {
                driver.get(this.companyDto.getCompanyUrl().getUrl() + "?page=" + pageNum);
//                driver.findElement(contentsBy);
                By contentsBy = By.className("contents");
                WebElement element = CrawlingUtils.getWebElements(driver, contentsBy);
                crawlingItems.addAll(parseWebElement(element));
                pageNum += 1;
            } catch (Exception e) {
                log.error("naver tech 페이지 끝까지 다 누름 {}", pageNum);
                break;
            }

        }
        return crawlingItems;
    }

    @Override
    public List<CrawlingDto> parseWebElement(WebElement element) {
        if (element == null) {
            return new ArrayList<>();
        }
        return element.findElements(By.cssSelector(".post_article > .cont_post")).stream()
                .map(this::getCrawlingDto)
                .collect(Collectors.toList());
    }

    @Override
    public CrawlingDto getCrawlingDto(WebElement element) {
        String title = element.findElement(By.cssSelector("h2 > a")).getText();
        String thumnailUrl = element.findElement(By.cssSelector(".cont_img > a > img")).getAttribute("src");
        List<WebElement> bottomSpanElements = element.findElement(By.tagName("dl")).findElements(By.tagName("dd"));
        WebElement dateElement = bottomSpanElements.stream()
                .filter(webElement -> webElement.getText().contains("."))
                .findFirst().orElse(null);
        String date = dateElement==null ? "" : dateElement.getText();
        String url = element.findElement(By.cssSelector("h2 > a")).getAttribute("href");

        String NAVER_D2_SITE_URL = "https://d2.naver.com";

        return CrawlingDto.builder()
                .companyUrl(this.companyDto.getCompanyUrl())
                .title(title)
                .date(date)
                .thumbnailUrl(thumnailUrl)
                .contentUrl(NAVER_D2_SITE_URL + url)
                .build();
    }
}