package com.sky7th.devtimeline.batch.service.crawling;

import com.sky7th.devtimeline.batch.domain.company.Company;
import com.sky7th.devtimeline.batch.dto.CrawlingDto;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface CrawlingInterface {

    List<CrawlingDto> crawling(Company company);

    List<CrawlingDto> parseWebElement(WebElement element);

    CrawlingDto getCrawlingDto(WebElement element);

}
