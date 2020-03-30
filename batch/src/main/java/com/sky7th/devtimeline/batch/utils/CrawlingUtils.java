package com.sky7th.devtimeline.batch.utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Slf4j
public class CrawlingUtils {

    public static WebElement getWebElements(WebDriver driver, By by) {
        WebElement element = null;
        WebDriverWait driverWait = new WebDriverWait(driver, 40);
        try {
            try {
                driverWait.until(ExpectedConditions.visibilityOfElementLocated(by));

            } catch(TimeoutException e) {
                log.error("url: {} 시간초과", driver.getCurrentUrl());
                Thread.sleep(10000);
            }
            element = driver.findElement(by);

        } catch (NoSuchElementException one_more_try) {
            try {
                driverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
                element = driver.findElement(by);

            } catch (NoSuchElementException no_date_exist) {
                log.error("url: {} 재시도 실패", driver.getCurrentUrl());
                element = null;
            }

        } catch (Exception e) {
            log.error("url: {} 에러 발생", driver.getCurrentUrl());
        }

        return element;
    }

    public static void clickMoreBtnUntilTheEnd(WebDriver driver, By moreBtnBy) {
        while (true) {
            try {
                driver.findElement(moreBtnBy).click();
                Thread.sleep(1000L);
            } catch (Exception e) {
                log.error("naver 채용 더보기 끝까지 다 누름");
                break;
            }
        }
    }

}
