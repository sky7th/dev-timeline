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
public class NhnTechCrawlingService implements CompanyCrawlingService {

    private final WebDriver driver;

    private CompanyDto companyDto;

    @Override
    public List<CrawlingDto> crawling(CompanyDto companyDto) {
        this.companyDto = companyDto;

        return getAllCrawlingDtoUntilLastPage();
    }

    private List<CrawlingDto> getAllCrawlingDtoUntilLastPage() {
        List<CrawlingDto> crawlingItems = new ArrayList<>();
        int pageNum = 1;
        String title = "";
        while (true) {
            this.driver.get(this.companyDto.getCompanyUrl().getUrl() + "?page=" + pageNum);
            waitContentsChanged();
            WebElement element = CrawlingUtils.getWebElement(this.driver, By.className("lst_type"));
            List<WebElement> elements = element.findElements(By.cssSelector(".lst_item"));
            WebElement lastChild = elements.get(elements.size()-1);
            String nowTitle = lastChild.findElement(By.cssSelector(".tit")).getText();
            if (nowTitle.equals(title)) {
                break;
            }
            title = nowTitle;
            crawlingItems.addAll(parseWebElement(element));
            pageNum += 1;
        }

        return crawlingItems;
    }

    private void waitContentsChanged() {
        By firstContentTitle = By.cssSelector(".lst_type .lst_item .sec_box h3");
        CrawlingUtils.getWebElement(this.driver, firstContentTitle);
    }

    @Override
    public List<CrawlingDto> parseWebElement(WebElement element) {
        if (element == null) {
            return new ArrayList<>();
        }
        return element.findElements(By.cssSelector(".lst_item")).stream()
                .map(this::getCrawlingDto)
                .collect(Collectors.toList());
    }

    @Override
    public CrawlingDto getCrawlingDto(WebElement element) {
        String title = element.findElement(By.cssSelector(".tit")).getText();
        String date = element.findElement(By.cssSelector(".date")).getText().trim();
        String contentUrl = element.findElement(By.cssSelector("a")).getAttribute("href");
        Pattern p = Pattern.compile("(?<=/posts/).+", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(contentUrl);
        String key = m.find() ? m.group() : "";

        return CrawlingDto.builder()
                .crawlId(CompanyType.NHN.toString()+"-"+ CompanyUrlType.TECH.toString()+"-"+key)
                .companyUrl(this.companyDto.getCompanyUrl())
                .title(title)
                .author("")
                .date(date)
                .thumbnailUrl("")
                .contentUrl(contentUrl)
                .build();
    }
}