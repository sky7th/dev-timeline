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
public class LineTechCrawlingService implements CompanyCrawlingService {

    private final WebDriver driver;
    private CompanyDto companyDto;

    @Override
    public List<CrawlingDto> crawling(CompanyDto companyDto) {
        this.companyDto = companyDto;
        this.driver.get(companyDto.getCompanyUrl().getUrl());

        List<WebElement> webElements = this.driver.findElements(By.className("page-number"));
        int pageSize = Integer.parseInt(webElements.get(webElements.size()-2).getText());

        return getAllCrawlingDtoUntilLastPage(pageSize);
    }

    private List<CrawlingDto> getAllCrawlingDtoUntilLastPage(int pageSize) {
        List<CrawlingDto> crawlingItems = new ArrayList<>();

        for (int i = 1; i <= pageSize; i++) {
            this.driver.get(this.companyDto.getCompanyUrl().getUrl() + "/page/" + i);
            By by = By.id("post-list");
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
        return element.findElements(By.tagName("article")).stream()
                .map(this::getCrawlingDto)
                .collect(Collectors.toList());
    }

    @Override
    public CrawlingDto getCrawlingDto(WebElement element) {
        String title = element.findElement(By.cssSelector(".entry-title > a")).getText();
        String author = element.findElement(By.cssSelector(".entry-author .author-name")).getText().split("[|]")[0].trim();
        String contentUrl = element.findElement(By.cssSelector(".entry-title > a")).getAttribute("href");
        String date = element.findElement(By.cssSelector(".entry-author .author-name > .byline")).getText().split("[|]")[1].trim();;
        Pattern p = Pattern.compile("(?<=blog/).+(?=/)");
        Matcher matcher = p.matcher(contentUrl);
        String key = matcher.find() ? matcher.group() : "";

        return CrawlingDto.builder()
                .crawlId(CompanyType.LINE.toString()+"-"+ CompanyUrlType.TECH.toString()+"-"+key)
                .companyUrl(this.companyDto.getCompanyUrl())
                .title(title)
                .author(author)
                .date(date)
                .thumbnailUrl("")
                .contentUrl(contentUrl)
                .build();
    }

}
