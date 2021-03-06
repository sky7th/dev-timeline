package com.sky7th.devtimeline.batch.utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Slf4j
public class CrawlingUtils {

    public static WebElement getWebElement(WebElement targetElement, By by) {
        WebElement webElement = null;
        try {
            webElement = targetElement.findElement(by);
        } catch(NoSuchElementException e) {
            log.error("조건에 해당하는 element를 찾지 못했습니다.");
        }
        return webElement;
    }

    public static WebElement getWebElement(WebDriver driver, By by, int timeOutInSeconds) {
        WebElement contentsElement = null;
        WebDriverWait driverWait = new WebDriverWait(driver, timeOutInSeconds);
        try {
            driverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
            contentsElement = driver.findElement(by);
        } catch(TimeoutException e) {
            log.error("url: {} 시간초과({} 초), 해당 element 없음", driver.getCurrentUrl(), timeOutInSeconds);
        }

        return contentsElement;
    }

    public static WebElement getWebElement(WebDriver driver, By by) {
        WebElement element = null;
        WebDriverWait driverWait = new WebDriverWait(driver, 10);
        try {
            try {
                driverWait.until(ExpectedConditions.visibilityOfElementLocated(by));

            } catch(TimeoutException e) {
                log.error("url: {} 시간초과({} 초)", driver.getCurrentUrl(), 10);
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
        String display = "block";
        while (!display.equals("none")) {
            WebElement btnElement = driver.findElement(moreBtnBy);
            btnElement.click();
            try {
                Thread.sleep(1500L);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            display = driver.findElement(By.id("moreDiv")).getCssValue("display");
        }
        log.error("더보기 끝까지 다 누름");
    }

}
