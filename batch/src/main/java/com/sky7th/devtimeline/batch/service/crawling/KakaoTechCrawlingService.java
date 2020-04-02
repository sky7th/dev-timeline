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
public class KakaoTechCrawlingService implements CompanyCrawlingService {

    private final WebDriver driver;

    private CompanyDto companyDto;

    @Override
    public List<CrawlingDto> crawling(CompanyDto companyDto) {
        this.companyDto = companyDto;
        this.driver.get(companyDto.getCompanyUrl().getUrl());

        List<WebElement> webElements = this.driver.findElements(By.className("page-numbers"));
        int pageSize = Integer.parseInt(webElements.get(webElements.size()-2).getText());

        return getAllCrawlingDtoUntilLastPage(pageSize);
    }

    private List<CrawlingDto> getAllCrawlingDtoUntilLastPage(int pageSize) {
        List<CrawlingDto> crawlingItems = new ArrayList<>();

        for (int i = 1; i <= pageSize; i++) {
            this.driver.get(this.companyDto.getCompanyUrl().getUrl() + "/page/" + i);
            By by = By.className("list_post");
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
        WebElement thumnailUrlElement = CrawlingUtils.getWebElement(element, By.cssSelector(".link_post > .thumb_img > img"));

        String title = element.findElement(By.cssSelector(".link_post > .tit_post")).getText();
        String author = element.findElement(By.cssSelector(".info_writer > .area_txt > .inner_cell > .txt_name")).getText();
        String date = element.findElement(By.cssSelector(".link_post > .txt_date")).getText();
        String thumbnailUrl = thumnailUrlElement==null ? "" : thumnailUrlElement.getAttribute("src");
        String contentUrl = element.findElement(By.className("link_post")).getAttribute("href");

        return CrawlingDto.builder()
                .companyUrl(this.companyDto.getCompanyUrl())
                .title(title)
                .author(author)
                .date(date)
                .thumbnailUrl(thumbnailUrl)
                .contentUrl(contentUrl)
                .build();
    }

}
