package com.sky7th.devtimeline.batch.service.crawling;

import com.sky7th.devtimeline.batch.dto.CrawlingDto;
import com.sky7th.devtimeline.batch.dto.CompanyDto;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface CrawlingInterface {

    List<CrawlingDto> crawling(CompanyDto companyDto);

    List<CrawlingDto> parseWebElement(WebElement element);

    CrawlingDto getCrawlingDto(WebElement element);

}
