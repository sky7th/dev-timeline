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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ZumTechCrawlingService implements CompanyCrawlingService {

    private final WebDriver driver;
    private CompanyDto companyDto;

    @Override
    public List<CrawlingDto> crawling(CompanyDto companyDto) {
        this.companyDto = companyDto;

        return getAllCrawlingDtoUntilLastPage();
    }

    private List<CrawlingDto> getAllCrawlingDtoUntilLastPage() {
        this.driver.get(this.companyDto.getCompanyUrl().getUrl());
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Long height = (Long) js.executeScript("return document.body.scrollHeight");
        Long scroll = 0L;
        Long innerHeight = (Long) js.executeScript("return window.innerHeight");
        while (Math.abs(scroll + innerHeight - height) > 2) {
            js.executeScript("window.scrollBy(0, 100)");
            scroll = (Long) js.executeScript("return window.pageYOffset");
        }
        WebElement element = CrawlingUtils.getWebElement(this.driver, By.className("flex-grid"));

        return new ArrayList<>(parseWebElement(element));
    }

    @Override
    public List<CrawlingDto> parseWebElement(WebElement element) {
        if (element == null) {
            return new ArrayList<>();
        }
        return element.findElements(By.cssSelector("article")).stream()
                .map(this::getCrawlingDto)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public CrawlingDto getCrawlingDto(WebElement element) {
        String date_str = element.findElement(By.cssSelector("time")).getAttribute("datetime");
        String[] dates = date_str.split("T");
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        Date d = fm.parse(dates[0]);
        SimpleDateFormat fm2 = new SimpleDateFormat("yyyy.MM.dd");
        String date = fm2.format(d);
        String title = element.findElement(By.cssSelector(".text")).getText();
//        WebElement descriptionElement = element.findElement(By.cssSelector("a > .post-description"));
//        String description = descriptionElement==null ? "" : descriptionElement.getText();
        String contentUrl = element.findElement(By.cssSelector(".post-link")).getAttribute("href");
        Pattern p = Pattern.compile("(?<=io/).+(?=/)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(contentUrl);
        String key = m.find() ? m.group() : "";

        return CrawlingDto.builder()
                .crawlId(CompanyType.ZUM.toString()+"-"+ CompanyUrlType.TECH.toString()+"-"+key)
                .companyUrl(this.companyDto.getCompanyUrl())
                .title(title)
                .author("")
                .date(date)
                .thumbnailUrl("")
                .contentUrl(contentUrl)
                .build();
    }
}