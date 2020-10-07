package com.sky7th.devtimeline.batch.service.crawling.tech;

import com.sky7th.devtimeline.batch.dto.CompanyDto;
import com.sky7th.devtimeline.batch.dto.CrawlingDto;
import com.sky7th.devtimeline.batch.service.crawling.CompanyCrawlingService;
import com.sky7th.devtimeline.batch.utils.CrawlingUtils;
import com.sky7th.devtimeline.core.domain.company.domain.CompanyType;
import com.sky7th.devtimeline.core.domain.company.domain.CompanyUrlType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
            this.driver.get(this.companyDto.getCompanyUrl().getUrl() + "?page=" + pageNum);
            if (isNotExistNaverD2Contents())
                break;
            waitNaverD2ContentsChanged();
            WebElement element = CrawlingUtils.getWebElement(this.driver, By.className("contents"));
            crawlingItems.addAll(parseWebElement(element));
            pageNum += 1;
        }

        return crawlingItems;
    }

    private void waitNaverD2ContentsChanged() {
        By firstContentTitle = By.cssSelector(".contents > .post_article > .cont_post > h2 > a");
        CrawlingUtils.getWebElement(this.driver, firstContentTitle);
    }

    private boolean isNotExistNaverD2Contents() {
        By contentsBy = By.cssSelector(".contents > .post_article");
        WebElement webElement = CrawlingUtils.getWebElement(this.driver, contentsBy, 2);

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
        Pattern p = Pattern.compile("(?<=helloworld/).+", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(contentUrl);
        String key = m.find() ? m.group() : "";

        return CrawlingDto.builder()
                .crawlId(CompanyType.NAVER.toString()+"-"+ CompanyUrlType.TECH.toString()+"-"+key)
                .companyUrl(this.companyDto.getCompanyUrl())
                .title(title)
                .author("")
                .date(date)
                .thumbnailUrl(thumbnailUrl)
                .contentUrl(contentUrl)
                .build();
    }
}