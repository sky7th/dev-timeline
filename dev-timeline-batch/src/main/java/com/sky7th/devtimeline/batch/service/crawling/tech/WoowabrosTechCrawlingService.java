package com.sky7th.devtimeline.batch.service.crawling.tech;

import com.sky7th.devtimeline.batch.dto.CompanyDto;
import com.sky7th.devtimeline.batch.dto.CrawlingDto;
import com.sky7th.devtimeline.batch.service.crawling.CompanyCrawlingService;
import com.sky7th.devtimeline.batch.utils.CrawlingUtils;
import com.sky7th.devtimeline.crawlpost.company.domain.CompanyType;
import com.sky7th.devtimeline.crawlpost.company.domain.CompanyUrlType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        SimpleDateFormat fm2 = new SimpleDateFormat("yyyy.MM.dd");
        String date = fm2.format(d);
        String name;
        try {
            name = headers[2];
        } catch (ArrayIndexOutOfBoundsException e) {
            name = "";
        }
        String title = element.findElement(By.cssSelector("a > .post-link")).getText();
//        WebElement descriptionElement = element.findElement(By.cssSelector("a > .post-description"));
//        String description = descriptionElement==null ? "" : descriptionElement.getText();
        String contentUrl = element.findElement(By.cssSelector("a")).getAttribute("href");
        Pattern p = Pattern.compile("(?<=io/).+(?=\\.)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(contentUrl);
        String key = m.find() ? m.group() : "";

        return CrawlingDto.builder()
                .crawlId(CompanyType.WOOWABROS.toString()+"-"+ CompanyUrlType.TECH.toString()+"-"+key)
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