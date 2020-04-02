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
public class NaverTechCrawlingService implements CompanyCrawlingService {

    private final WebDriver driver;

    private CompanyDto companyDto;

    @Override
    public List<CrawlingDto> crawling(CompanyDto companyDto) {
        this.companyDto = companyDto;

        return getAllCrawlingDtoUntilLastPage();
    }

    private List<CrawlingDto> getAllCrawlingDtoUntilLastPage() {
        List<CrawlingDto> crawlingItems = new ArrayList<>();
        int pageNum = 0;

        while (true) {
            driver.get(this.companyDto.getCompanyUrl().getUrl() + "?page=" + pageNum);
            if (isNotExistNaverD2Contents())
                break;
            waitNaverD2ContentsChanged();
            WebElement element = CrawlingUtils.getWebElement(driver, By.className("contents"));
            crawlingItems.addAll(parseWebElement(element));
            pageNum += 1;
        }

        return crawlingItems;
    }

    private void waitNaverD2ContentsChanged() {
        By firstContentTitle = By.cssSelector(".contents > .post_article > .cont_post > h2 > a");
        CrawlingUtils.getWebElement(driver, firstContentTitle);
    }

    private boolean isNotExistNaverD2Contents() {
        By contentsBy = By.cssSelector(".contents > .post_article");
        WebElement webElement = CrawlingUtils.getWebElement(driver, contentsBy, 2);

        return webElement == null;
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
        WebElement thumnailUrlElement = CrawlingUtils.getWebElement(element, By.cssSelector(".cont_img > a > img"));
        List<WebElement> bottomSpanElements = element.findElement(By.tagName("dl")).findElements(By.tagName("dd"));
        WebElement dateElement = bottomSpanElements.stream()
                .filter(webElement -> webElement.getText().contains("."))
                .findFirst().orElse(null);

        String title = element.findElement(By.cssSelector("h2 > a")).getText();
        String date = dateElement==null ? "" : dateElement.getText();
        String thumbnailUrl = thumnailUrlElement==null ? "" : thumnailUrlElement.getAttribute("src");
        String contentUrl = element.findElement(By.cssSelector("h2 > a")).getAttribute("href");

        String NAVER_D2_SITE_URL = "https://d2.naver.com";
        return CrawlingDto.builder()
                .companyUrl(this.companyDto.getCompanyUrl())
                .title(title)
                .author("")
                .date(date)
                .thumbnailUrl(thumbnailUrl)
                .contentUrl(NAVER_D2_SITE_URL + contentUrl)
                .build();
    }
}