package com.sky7th.devtimeline.batch.utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Slf4j
public class CrawlingUtils {

    public static WebElement getWebElements(WebDriver driver, String url, By by) {
        WebElement element = null;
        WebDriverWait driverWait = new WebDriverWait(driver, 40);
        try {
            try {
                driverWait.until(ExpectedConditions.visibilityOfElementLocated(by));

            } catch(TimeoutException e) {
                log.error("url: {} 시간초과", url);
                Thread.sleep(10000);
            }
            element = driver.findElement(by);

        } catch (NoSuchElementException one_more_try) {
            try {
                driverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
                element = driver.findElement(by);

            } catch (NoSuchElementException no_date_exist) {
                log.error("url: {} 재시도 실패", url);
                element = null;
            }

        } catch (Exception e) {
            log.error("url: {} 에러 발생", url);
        }

        return element;
    }

    public static void clickMoreBtnUntilTheEnd(WebDriver driver, By by) {
        while (true) {
            try {
                driver.findElement(by).click();
                Thread.sleep(500L);
            } catch (Exception e) {
                log.error("naver 채용 더보기 끝까지 다 누름");
                break;
            }
        }
    }

}
