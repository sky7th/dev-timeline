package com.sky7th.devtimeline.batch.service.crawling;

import com.sky7th.devtimeline.batch.dto.CrawlingDto;
import com.sky7th.devtimeline.batch.utils.CrawlingUtils;
import com.sky7th.devtimeline.core.domain.dto.CompanyDto;
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
public class KakaoCrawlingService implements CrawlingInterface {

    private final WebDriver driver;

    private CompanyDto companyDto;

    @Override
    public List<CrawlingDto> crawling(CompanyDto companyDto) {
        this.companyDto = companyDto;
        driver.get(companyDto.getCompanyUrl().getUrl());

        int pageSize = Integer.parseInt(driver.findElement(By.className("btn_lst"))
                .getAttribute("href").split("=")[1]);

        return getAllCrawlingDtoUntilLastPage(pageSize);
    }

    private List<CrawlingDto> getAllCrawlingDtoUntilLastPage(int pageSize) {
        List<CrawlingDto> crawlingItems = new ArrayList<>();

        for (int i = 1; i <= pageSize; i++) {
            driver.get(this.companyDto.getCompanyUrl().getUrl() + "?page=" + i);
            By by = By.className("list_notice");
            WebElement element = CrawlingUtils.getWebElements(driver, by);
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
        String url = element.findElement(By.tagName("a")).getAttribute("href");

        return CrawlingDto.builder()
                .companyUrl(this.companyDto.getCompanyUrl())
                .title(title)
                .url("https://careers.kakao.com" + url)
                .build();
    }

}
