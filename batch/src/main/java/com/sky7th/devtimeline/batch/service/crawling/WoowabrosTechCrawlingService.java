package com.sky7th.devtimeline.batch.service.crawling;

import com.sky7th.devtimeline.batch.dto.CompanyDto;
import com.sky7th.devtimeline.batch.dto.CrawlingDto;
import com.sky7th.devtimeline.batch.utils.CrawlingUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class WoowabrosTechCrawlingService implements CompanyCrawlingService {

    private final WebDriver driver;
    private CompanyDto companyDto;

    @Override
    public List<CrawlingDto> crawling(CompanyDto companyDto) {
        this.companyDto = companyDto;

        return getAllCrawlingDtoUntilLastPage();
    }

    private List<CrawlingDto> getAllCrawlingDtoUntilLastPage() {
        this.driver.get(this.companyDto.getCompanyUrl().getUrl());
        WebElement element = CrawlingUtils.getWebElement(this.driver, By.className("list"));

        return new ArrayList<>(parseWebElement(element));
    }

    @Override
    public List<CrawlingDto> parseWebElement(WebElement element) {
        if (element == null) {
            return new ArrayList<>();
        }
        return element.findElements(By.cssSelector(".list-module")).stream()
                .map(this::getCrawlingDto)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public CrawlingDto getCrawlingDto(WebElement element) {
        String header = element.findElement(By.cssSelector(".post-meta")).getText();
        String[] headers = header.split(",");
        SimpleDateFormat fm = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
        Date d = fm.parse(headers[0] + ", " + headers[1].trim());
        SimpleDateFormat fm2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = fm2.format(d);
        String name = headers[2];
        String title = element.findElement(By.cssSelector("a > .post-link")).getText();
//        WebElement descriptionElement = element.findElement(By.cssSelector(".post-description"));
//        String description = descriptionElement==null ? "" : descriptionElement.getText();
        String contentUrl = element.findElement(By.cssSelector("a")).getAttribute("href");

        return CrawlingDto.builder()
                .companyUrl(this.companyDto.getCompanyUrl())
                .title(title)
//                .description(description)
                .author(name)
                .date(date)
                .thumbnailUrl("")
                .contentUrl(contentUrl)
                .build();
    }
}