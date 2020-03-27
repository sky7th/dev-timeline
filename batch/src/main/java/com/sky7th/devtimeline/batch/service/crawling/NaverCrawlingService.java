package com.sky7th.devtimeline.batch.service.crawling;

import com.sky7th.devtimeline.batch.domain.company.Company;
import com.sky7th.devtimeline.batch.dto.CrawlingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class NaverCrawlingService {

    private final WebDriver webDriver;

    public List<CrawlingDto> crawling(Company company) {
        readyToCrawling(company.getUrl());

        return parsing(company);
    }

    private void readyToCrawling(String url) {
        webDriver.get(url);
        clickMoreBtnUntilTheEnd();
    }

    private void clickMoreBtnUntilTheEnd() {
        while (true) {
            try {
                webDriver.findElement(By.className("more_btn")).click();
                Thread.sleep(500L);
            } catch (Exception e) {
                break;
            }
        }
    }

    private List<CrawlingDto> parsing(Company company) {
        List<Document> documents = Collections.singletonList(Jsoup.parse(webDriver.getPageSource()));
        Document document = documents.stream().findFirst().orElseThrow(NotFoundException::new);

        return document.getElementById("jobListDiv").child(0).children().stream()
                .map(li -> getCrawlingDto(company, li))
                .collect(Collectors.toList());
    }

    private CrawlingDto getCrawlingDto(Company company, Element li) {
        return CrawlingDto.builder()
                .name(company.getName())
                .title(li.getElementsByClass("crd_tit").first().text())
                .date(li.getElementsByClass("crd_date").first().text())
                .url("https://recruit.navercorp.com" + li.getElementsByTag("a").first().attr("href"))
                .build();
    }
}