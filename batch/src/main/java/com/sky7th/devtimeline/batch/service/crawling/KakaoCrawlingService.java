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
public class KakaoCrawlingService {

    private final WebDriver driver;

    private Company company;

    public List<CrawlingDto> crawling(Company company) {
        this.company = company;
        driver.get(company.getUrl());

        int pageSize = Integer.parseInt(driver.findElement(By.className("btn_lst"))
                .getAttribute("href").split("=")[1]);

        return getAllCrawlingDtoUntilLastPage(pageSize);
    }

    private List<CrawlingDto> getAllCrawlingDtoUntilLastPage(int pageSize) {
        List<CrawlingDto> crawlingItems = new ArrayList<>();

        for (int i = 1; i <= pageSize; i++) {
            driver.get(this.company.getUrl() + "?page=" + i);
            By by = By.className("list_notice");
            WebElement element = CrawlingUtils.getWebElements(driver, this.company.getUrl(), by);
            crawlingItems.addAll(parseWebElement(element));
        }
        return crawlingItems;
    }

    private List<CrawlingDto> parseWebElement(WebElement element) {
        if (element == null) {
            return new ArrayList<>();
        }
        return element.findElements(By.tagName("li")).stream()
                .map(this::getCrawlingDto)
                .collect(Collectors.toList());
    }

    private CrawlingDto getCrawlingDto(WebElement li) {
        String title = li.findElement(By.className("txt_tit")).getText();
        String url = li.findElement(By.tagName("a")).getAttribute("href");

        return CrawlingDto.builder()
                .name(this.company.getName())
                .title(title)
                .url("https://careers.kakao.com" + url)
                .build();
    }

}
